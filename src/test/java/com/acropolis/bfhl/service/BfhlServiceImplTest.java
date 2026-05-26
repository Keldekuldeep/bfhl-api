package com.acropolis.bfhl.service;

import com.acropolis.bfhl.dto.BfhlResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class BfhlServiceImplTest {

    private BfhlServiceImpl service;

    @BeforeEach
    void setUp() {
        service = new BfhlServiceImpl();
    }

    // ── curl Example A: ["a", "1", "334", "4", "R", "$"] ──────────────────
    @Test
    void givenExampleA_whenProcessed_thenAllFieldsCorrect() {
        BfhlResponse r = service.processData(List.of("a", "1", "334", "4", "R", "$"));

        assertThat(r.isSuccess()).isTrue();
        assertThat(r.getOddNumbers()).containsExactly("1");
        assertThat(r.getEvenNumbers()).containsExactly("334", "4");
        assertThat(r.getAlphabets()).containsExactly("A", "R");
        assertThat(r.getSpecialCharacters()).containsExactly("$");
        assertThat(r.getSum()).isEqualTo("339");
        assertThat(r.getConcatString()).isEqualTo("Ra");
    }

    // ── curl Example B: ["2","a","y","4","&","-","*","5","92","b"] ─────────
    // numbers: 2(even),4(even),5(odd),92(even) → sum=103
    // alphabets: a,y,b → upper=[A,Y,B], concat reverse("ayb")="bya" → "ByA"
    // specials: &, -, *
    @Test
    void givenExampleB_whenProcessed_thenAllFieldsCorrect() {
        BfhlResponse r = service.processData(
                List.of("2", "a", "y", "4", "&", "-", "*", "5", "92", "b"));

        assertThat(r.getOddNumbers()).containsExactly("5");
        assertThat(r.getEvenNumbers()).containsExactly("2", "4", "92");
        assertThat(r.getAlphabets()).containsExactly("A", "Y", "B");
        assertThat(r.getSpecialCharacters()).containsExactly("&", "-", "*");
        assertThat(r.getSum()).isEqualTo("103");
        assertThat(r.getConcatString()).isEqualTo("ByA");
    }

    // ── curl Example C: ["A", "ABCD", "DOE"] ──────────────────────────────
    // chars: A,A,B,C,D,D,O,E → concat="AABCDDOE" → reversed="EODDCBAA"
    // alternating: E,o,D,d,C,b,A,a → "EoDdCbAa"
    @Test
    void givenExampleC_whenProcessed_thenAllFieldsCorrect() {
        BfhlResponse r = service.processData(List.of("A", "ABCD", "DOE"));

        assertThat(r.getAlphabets()).containsExactly("A", "ABCD", "DOE");
        assertThat(r.getOddNumbers()).isEmpty();
        assertThat(r.getEvenNumbers()).isEmpty();
        assertThat(r.getSpecialCharacters()).isEmpty();
        assertThat(r.getSum()).isEqualTo("0");
        assertThat(r.getConcatString()).isEqualTo("EoDdCbAa");
    }

    // ── curl Numbers only: ["2", "8", "13"] ───────────────────────────────
    @Test
    void givenNumbersOnly_whenProcessed_thenAlphabetsAndSpecialsEmpty() {
        BfhlResponse r = service.processData(List.of("2", "8", "13"));

        assertThat(r.getEvenNumbers()).containsExactly("2", "8");
        assertThat(r.getOddNumbers()).containsExactly("13");
        assertThat(r.getAlphabets()).isEmpty();
        assertThat(r.getSpecialCharacters()).isEmpty();
        assertThat(r.getSum()).isEqualTo("23");
        assertThat(r.getConcatString()).isEqualTo("");
    }

    // ── curl Special characters only: ["$", "@", "#"] ─────────────────────
    @Test
    void givenSpecialCharsOnly_whenProcessed_thenNumbersAndAlphabetsEmpty() {
        BfhlResponse r = service.processData(List.of("$", "@", "#"));

        assertThat(r.getSpecialCharacters()).containsExactly("$", "@", "#");
        assertThat(r.getOddNumbers()).isEmpty();
        assertThat(r.getEvenNumbers()).isEmpty();
        assertThat(r.getAlphabets()).isEmpty();
        assertThat(r.getSum()).isEqualTo("0");
        assertThat(r.getConcatString()).isEqualTo("");
    }

    // ── Additional logic tests ─────────────────────────────────────────────

    @Test
    void givenNoAlphabets_whenProcessed_thenConcatStringIsEmpty() {
        BfhlResponse r = service.processData(List.of("1", "2", "$"));
        assertThat(r.getConcatString()).isEqualTo("");
    }

    @Test
    void givenNoNumbers_whenProcessed_thenSumIsZero() {
        BfhlResponse r = service.processData(List.of("a", "$"));
        assertThat(r.getSum()).isEqualTo("0");
    }

    @Test
    void givenOddAndEvenNumbers_whenProcessed_thenCorrectlySeparated() {
        BfhlResponse r = service.processData(List.of("1", "2"));
        assertThat(r.getOddNumbers()).containsExactly("1");
        assertThat(r.getEvenNumbers()).containsExactly("2");
    }

    @Test
    void givenMultiCharAlphabet_whenProcessed_thenTreatedAsAlphabetic() {
        BfhlResponse r = service.processData(List.of("ABCD"));
        assertThat(r.getAlphabets()).containsExactly("ABCD");
        assertThat(r.getSpecialCharacters()).isEmpty();
    }

    @Test
    void givenLowercaseAlphabet_whenProcessed_thenAlphabetsListIsUppercase() {
        BfhlResponse r = service.processData(List.of("a"));
        assertThat(r.getAlphabets()).containsExactly("A");
    }

    @Test
    void givenExampleA_whenProcessed_thenConcatStringIsRa() {
        BfhlResponse r = service.processData(List.of("a", "R"));
        assertThat(r.getConcatString()).isEqualTo("Ra");
    }
}
