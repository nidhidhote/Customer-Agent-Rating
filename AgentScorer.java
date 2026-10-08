import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class AgentScorer {
    /*
    * Agent Rating
    *rate the agent from 1 to 5
    *return all the agents along with their ratings from highest to lowest
    * Follow up 1: tie-breaker test case if two agent have same ratings
    * Follow up 2: Wanted month wise report of highest rated agent
    * */
    public static final  Integer INT_MIN = 1;
    public static final Integer INT_MAX = 5;
    Map<Integer, AgentRating> agentRatingMap = new HashMap<>();
    public void setRatings(int agentId, int rating){
        //data validations
        if (rating<INT_MIN || rating >INT_MAX) throw new IllegalArgumentException();
        AgentRating ar = agentRatingMap.computeIfAbsent(agentId,AgentRating::new);
        ar.addRating(rating);
    }
    public List<AgentRating> getRatings(){
        // cases: if two agents will have same ratings, then sort on the basis of countofratings else if countofrating is also same then sort on the basis of agentid
       Comparator<AgentRating>agentRatingComparator = Comparator.comparing(AgentRating::getAverageRating).thenComparing(AgentRating::getCountOfRatings).thenComparing(AgentRating::getAgentId).reversed();
       return agentRatingMap.values().stream().sorted(agentRatingComparator).collect(Collectors.toList());
    }
    class AgentRating{
       private int agentId;
       private Long totalRating;
       private int countOfRatings;
       AgentRating(int agentId){
           this.agentId=agentId;
           this.totalRating = 0L;
       }
       public double getAverageRating(){
           return (double)this.totalRating/this.countOfRatings;
       }
       public void addRating(int rating){
           this.totalRating+=rating;
           this.countOfRatings++;
       }
       public int getAgentId(){
           return this.agentId;
       }
       public int getCountOfRatings(){
           return this.countOfRatings;
       }
        @Override
        public String  toString(){
            return "agentId"+getAgentId() +" ratings "+ getAverageRating();
        }
    }
    public static void main(String args[]){
    // test cases
        AgentScorer agentScorer = new AgentScorer();
        agentScorer.setRatings(1,4);
        agentScorer.setRatings(1,4);

       // System.out.println(agentScorer.getRatings());
        agentScorer.setRatings(2,4);
        agentScorer.setRatings(2,4);
        agentScorer.setRatings(2,4);
        agentScorer.setRatings(2,4);
        System.out.println(agentScorer.getRatings());


    }
}

