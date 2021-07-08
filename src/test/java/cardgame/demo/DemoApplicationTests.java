package cardgame.demo;


import cardgame.schemas.*;
import cardgame.schemas.Number;
import cardgame.services.dealerservices.Combinations;
import cardgame.services.dealerservices.DealerHelper;
import cardgame.services.dealerservices.Dealer;
import cardgame.services.dealerservices.SimpleWinnerStrategy;
import cardgame.services.dealerservices.SimpleDealerHelper;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.HashSet;


@RunWith(SpringRunner.class)
@Import(Config.class)
@ContextConfiguration(classes = {cardgame.schemas.Deck.class,
                                 Combinations.class,
                                 SimpleWinnerStrategy.class,
                                 DealerHelper.class})
@ComponentScan(basePackages = {"cardgame", "cardgame.services", "cardgame.schemas"})
@Profile("test")
@SpringBootTest
class DemoApplicationTests {
    @Autowired
    Combinations combinations;


    DealerHelper dealerHelper = new SimpleDealerHelper();


    Dealer dealer = new Dealer();


    @Autowired
    SimpleWinnerStrategy simpleWinnerStrategy;

    @Test
    void contextLoads() {
    }

    @Test
    void checkCardsInDeck() {
        Deck deck = new Deck();
        Assert.assertEquals(deck.getCards().size(), 52);
        Assert.assertEquals(deck.getCards().stream().filter(card -> card.getSuit().equals(Suit.CLUB)).count(), 13);
        Assert.assertEquals(deck.getCards().stream().filter(card -> card.getSuit().equals(Suit.DIAMOND)).count(), 13);
        Assert.assertEquals(deck.getCards().stream().filter(card -> card.getSuit().equals(Suit.SPADE)).count(), 13);
        Assert.assertEquals(deck.getCards().stream().filter(card -> card.getSuit().equals(Suit.HEART)).count(), 13);
        Assert.assertEquals(deck.getCards().stream().filter(card -> card.getNumber().equals(Number.ACE)).count(), 4);
        Assert.assertEquals(deck.getCards().stream().filter(card -> card.getNumber().equals(Number.TWO)).count(), 4);
        Assert.assertEquals(deck.getCards().stream().filter(card -> card.getNumber().equals(Number.THREE)).count(), 4);
        Assert.assertEquals(deck.getCards().stream().filter(card -> card.getNumber().equals(Number.FOUR)).count(), 4);
        Assert.assertEquals(deck.getCards().stream().filter(card -> card.getNumber().equals(Number.FIVE)).count(), 4);
        Assert.assertEquals(deck.getCards().stream().filter(card -> card.getNumber().equals(Number.SIX)).count(), 4);
        Assert.assertEquals(deck.getCards().stream().filter(card -> card.getNumber().equals(Number.SEVEN)).count(), 4);
        Assert.assertEquals(deck.getCards().stream().filter(card -> card.getNumber().equals(Number.EIGHT)).count(), 4);
        Assert.assertEquals(deck.getCards().stream().filter(card -> card.getNumber().equals(Number.NINE)).count(), 4);
        Assert.assertEquals(deck.getCards().stream().filter(card -> card.getNumber().equals(Number.TEN)).count(), 4);
        Assert.assertEquals(deck.getCards().stream().filter(card -> card.getNumber().equals(Number.JACK)).count(), 4);
        Assert.assertEquals(deck.getCards().stream().filter(card -> card.getNumber().equals(Number.QUEEN)).count(), 4);
        Assert.assertEquals(deck.getCards().stream().filter(card -> card.getNumber().equals(Number.KING)).count(), 4);
    }

    @Test
    void checkRandomNess(){
        HashSet<Card> cards = new HashSet<>();
        Deck deck = new Deck();
        int drawCount = 0;
        while(!deck.getCards().isEmpty()) {
            Card card = deck.drawCard();
            Assert.assertFalse(cards.contains(card));
            cards.add(card);
            drawCount++;
        }
        Assert.assertEquals(drawCount, 52);
    }
    @Test
    void tableShouldNotHaveAnyPlayerAtLoading() {
        //Assert.assertFalse(table.getPlayers().stream().findAny().isPresent());
    }

    @Test
    void isTrailWorkingAsExpected() {
        ArrayList<Card> cards = new ArrayList<>();
        cards.add(new Card(Suit.HEART, Number.EIGHT));
        cards.add(new Card(Suit.SPADE, Number.EIGHT));
        cards.add(new Card(Suit.HEART, Number.EIGHT));

        Assert.assertTrue(combinations.isTrail(cards));
    }

    @Test
    void isTrailNotWorkingAsExpected() {
        ArrayList<Card> cards = new ArrayList<>();
        cards.add(new Card(Suit.CLUB, Number.EIGHT));
        cards.add(new Card(Suit.HEART, Number.NINE));
        cards.add(new Card(Suit.DIAMOND, Number.EIGHT));

        Assert.assertFalse(combinations.isTrail(cards));
    }

    @Test
    void isSequenceWorkingAsExpected() {
        ArrayList<Card> cards = new ArrayList<>();
        cards.add(new Card(Suit.CLUB, Number.EIGHT));
        cards.add(new Card(Suit.HEART, Number.NINE));
        cards.add(new Card(Suit.DIAMOND, Number.TEN));

        Assert.assertTrue(combinations.isSequence(cards));
    }

    @Test
    void isSequenceWorkingAsExpected2() {
        ArrayList<Card> cards = new ArrayList<>();
        cards.add(new Card(Suit.SPADE, Number.QUEEN));
        cards.add(new Card(Suit.DIAMOND, Number.TEN));
        cards.add(new Card(Suit.SPADE, Number.JACK));

        Assert.assertTrue(combinations.isSequence(cards));
    }

    @Test
    void isSequenceNotWorkingAsExpected() {
        ArrayList<Card> cards = new ArrayList<>();
        cards.add(new Card(Suit.CLUB, Number.EIGHT));
        cards.add(new Card(Suit.HEART, Number.TWO));
        cards.add(new Card(Suit.DIAMOND, Number.TEN));

        Assert.assertFalse(combinations.isSequence(cards));
    }

    @Test
    void isPairWorkingAsExpected() {
        ArrayList<Card> cards = new ArrayList<>();
        cards.add(new Card(Suit.CLUB, Number.TEN));
        cards.add(new Card(Suit.HEART, Number.TWO));
        cards.add(new Card(Suit.DIAMOND, Number.TEN));
        Assert.assertTrue(combinations.getPair(cards).getKey());
        Assert.assertEquals(combinations.getPair(cards).getValue().getNumber(), Number.TEN);
    }

    @Test
    void isPairNotWorkingAsExpected() {
        ArrayList<Card> cards = new ArrayList<>();
        cards.add(new Card(Suit.CLUB, Number.FOUR));
        cards.add(new Card(Suit.HEART, Number.TWO));
        cards.add(new Card(Suit.DIAMOND, Number.TEN));
        Assert.assertFalse(combinations.getPair(cards).getKey());
    }

    @Test
    void isTopCardWorkingAsExpected() {
        ArrayList<Card> cards = new ArrayList<>();
        cards.add(new Card(Suit.CLUB, Number.ACE));
        cards.add(new Card(Suit.HEART, Number.KING));
        cards.add(new Card(Suit.DIAMOND, Number.JACK));
        Assert.assertEquals(combinations.getTopCard(cards).getNumber(), Number.KING);
    }

    @Test
    void isBestCombinationReturningTrail() {
        Player player = new Player(new User(1, "p1"), 3);
        player.getCards().add(new Card(Suit.CLUB, Number.EIGHT));
        player.getCards().add(new Card(Suit.HEART, Number.EIGHT));
        player.getCards().add(new Card(Suit.DIAMOND, Number.EIGHT));
        simpleWinnerStrategy.computeBestCombination(player);
        Assert.assertEquals(player.getRank().getCards()[0].getNumber(), Number.EIGHT);
    }

    @Test
    void isBestCombinationReturningSequence() {
        Player player = new Player(new User(1, "p1"), 3);
        player.getCards().add(new Card(Suit.CLUB, Number.NINE));
        player.getCards().add(new Card(Suit.HEART, Number.TEN));
        player.getCards().add(new Card(Suit.DIAMOND, Number.EIGHT));
        simpleWinnerStrategy.computeBestCombination(player);
        Assert.assertEquals(player.getRank().getCards()[0].getNumber(), Number.TEN);
    }



    @Test
    void isBestCombinationReturningTopCard() {
        Player player = new Player(new User(1, "p1"), 3);
        player.getCards().add(new Card(Suit.CLUB, Number.EIGHT));
        player.getCards().add(new Card(Suit.HEART, Number.TWO));
        player.getCards().add(new Card(Suit.DIAMOND, Number.ACE));
        simpleWinnerStrategy.computeBestCombination(player);
        Assert.assertEquals(player.getRank().getCards()[0].getNumber(), Number.EIGHT);
    }








    }
