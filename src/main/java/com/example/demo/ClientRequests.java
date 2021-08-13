package com.example.demo;
import java.net.URI;
import java.util.*;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

public class ClientRequests {

    public static final String SERVER_URI_BID = "http://localhost:8081/api/bid";
    public static final String SERVER_URI_AUCTION = "http://localhost:8081/api/auction";
    public static final String SERVER_URI_GENERATE = "http://localhost:8081/api/generate";
    private static String username;
    @JsonProperty("auctioneer")
    private static int auctioneer; //Detta borde finnas hos servern egentligen
    private static Person person;
    private static String chosenAuction;


    public static void main(String args[]) {
        postAuction(20);
        /*postAuction(20);
        postAuction(20);
        postBid2(2000);
        Scanner scanner = new Scanner(System.in);
        personSettings(scanner);
        while(true)
            interaction(scanner);
*/
    }

    private static void interaction(Scanner scanner){

        presentAndChooseAuctions(scanner);
        presentAllBids();
        System.out.println("Write in a number to bid and . to chose a new auction");
        String command = scanner.nextLine();
        if (command.equals("."))
            return;
        try {
            postBid2(Integer.parseInt(command));
        }
        catch (NumberFormatException n) {
            System.out.println(n);
            return;
        }
    }

    public static void personSettings(Scanner scanner) {
        System.out.println("What is your name?");
        String name = scanner.nextLine();
        Person person = new Person(name);
        ClientRequests.person = person;
    }

    public static void presentAndChooseAuctions(Scanner scanner) {
        System.out.println("Which of these auctions do you want to attend?");
        ArrayList<LinkedHashMap> auctions = getAllAuctions();
        for(LinkedHashMap auction:auctions){
            System.out.println(auction);
        }
        ClientRequests.chosenAuction = scanner.nextLine();
        System.out.println("You have chosen auction: " + ClientRequests.chosenAuction);
    }

    private static void getHighestBid() {
        RestTemplate restTemplate = new RestTemplate();
        ArrayList<LinkedHashMap> emps = restTemplate.getForObject(SERVER_URI_BID, ArrayList.class);
        System.out.println(emps.size());
        for (LinkedHashMap map : emps) {
            System.out.println(map);
        }
    }

    private static ArrayList<LinkedHashMap> getAllAuctions() {

        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(SERVER_URI_AUCTION, ArrayList.class);
    }

    private static void presentAllBids() {
        System.out.println("These are the bids for auction " + chosenAuction + ":");
        RestTemplate restTemplate = new RestTemplate();
        //we can't get List<> because JSON convertor doesn't know the type of
        //object in the list and hence convert it to default JSON object type LinkedHashMap

        ArrayList<LinkedHashMap> emps = restTemplate.getForObject(SERVER_URI_BID, ArrayList.class);

        System.out.println(emps.size());
        for (LinkedHashMap map : emps) {
            System.out.println(map);
        }
    }

    /*private static void postBid(int amount) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON); //Header säger hur det ska tolkas

        Bid bid = new Bid(username, amount);

        restTemplate.postForObject(SERVER_URI_BID, bid, Bid.class);
        //getAllBids();
    }*/

    private static void postBid2(int amount) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON); //Header säger hur det ska tolkas


        int bidId = restTemplate.getForObject(SERVER_URI_GENERATE, Integer.class);

        PostBidTemplate postBidTemplate = new PostBidTemplate(2,amount, bidId);
        restTemplate.postForObject(SERVER_URI_BID, postBidTemplate, PostBidTemplate.class);
    }

    private static void postAuction(int auctioneer) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON); //Header säger hur det ska tolkas

        //MultiValueMap<String, String> map= new LinkedMultiValueMap<String, String>();
        //map.add("amount", "2");

        Integer auctioneerInt = auctioneer;

        restTemplate.postForObject(SERVER_URI_AUCTION, auctioneerInt, Integer.class);
    }

}