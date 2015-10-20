package pokerBase;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import pokerEnums.eRank;
import pokerEnums.eSuit;
import pokerBase.Card;
@XmlRootElement
public class Deck {
	
	@XmlElement (name="Remaining Card")
	private ArrayList<Card> cards;
	//i broke the code and this is the only way i could fix it
	private int Wildcard;
	private Random r = new Random();
	
	public Deck(boolean Wildcard) {
		
		
		//	Create an ArrayList of Cards, add each card
		ArrayList<Card> MakingDeck = new ArrayList<Card>();
		int x = r.nextInt(12)+1;
		for (short i = 0; i <= 3; i++) {
			eSuit SValue = eSuit.values()[i];
			for (short j = 0; j <= 12; j++) {
				if (Wildcard){					
					if (j != x){
						this.setWildC(j);
						eRank RValue = eRank.values()[j];
						Card newCard = new Card(SValue,RValue,(13*i)+j+1);
						MakingDeck.add(newCard);
					}
					else {
						eRank RankValue = eRank.values()[j];				
						Card NewCard = new Card(SValue,RankValue,true);
						MakingDeck.add(NewCard);
					}
				}
					
				else {
				eRank RankValue = eRank.values()[j];				
				Card NewCard = new Card(SValue,RankValue, (13 * i) + j+1);
				MakingDeck.add(NewCard);
			}
			}
		}
		//	Set the instance variable
		cards = MakingDeck;
		ShuffleCards();

	}
	public Deck(int jokers, boolean Wildcard){
		this(Wildcard);
		for (short i=0;i<jokers;++i){
			Card Joker = new Card(eSuit.JOKER,eRank.JOKER,i+1);
			cards.add(Joker);
		}
	}
	
	
	private void ShuffleCards()
	{
		//	Shuffle the cards
		Collections.shuffle(cards);
	}

	public Card drawFromDeck() {
		// Removes the first card from the deck and return the card
		Card FirstCard = cards.get(0);
		cards.remove(0);
		return FirstCard;
	}

	public int getTotalCards() {
		// Returns the total number of cards still in the deck
		return cards.size();
	}
	
	public ArrayList<Card> getCards()
	{
		return this.cards;
	}
	
	public StringWriter SerializeMe()
	{
	    StringWriter sw = new StringWriter();
		try
		{
		    //Write it
		    JAXBContext ctx = JAXBContext.newInstance(Deck.class);
		    Marshaller m = ctx.createMarshaller();
		    m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		    m.marshal(this, sw);
		    sw.close();			
		}
		catch (Exception ex)
		{
			
		}
    
    return sw;
	}
	public int getWildC(){
		return Wildcard;
	}
	public void setWildC(int Wildcard){
		this.Wildcard = Wildcard;
	}
}