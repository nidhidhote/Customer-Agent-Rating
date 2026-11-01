import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class AgentScorer {
   /*
   * 1. give rating to customer support agent in between 1-5,
   * 2. all the customer agents with their ratings from top to bottom, meaning whoever agent will be having maximum average rating will be the top agent
   * Follow up-
   * 1. tiebreaker
   * 2. month-wise who is the top rated customer agent
   * */
    public final static Integer INT_MIN = 1;
    public final static Integer INT_MAX=5;

    public Map<Integer,AgentRating>agentRatingMap = new HashMap<>();
    public void giveRating(int agentId, int rating){
        //validate the arguments
        if (rating< INT_MIN || rating> INT_MAX)throw  new IllegalArgumentException("Invalid ratings");
        //validate agentId, if agent id is not present then add it in the map
        AgentRating agentRating = agentRatingMap.computeIfAbsent(agentId,AgentRating::new);
        agentRating.setRating(rating);
    }
    //return list of all the agents with their ratings from highest to lowest
    public List<AgentRating>GetRatings(){
        //two agents have same ratings->get count of rating
        // if count and average are same take agent id as a source to decide
        Comparator <AgentRating>agentRatingComparator = Comparator.comparing(AgentRating::getAverageRating).thenComparing(AgentRating::getCountOfRating).thenComparing(AgentRating::getAgentId).reversed();
        return agentRatingMap.values().stream().sorted(agentRatingComparator).collect(Collectors.toList());
    }
    public class AgentRating{
        private int agentId;
        private int countOfRating;
        private long totalRating;

        AgentRating(int agentId){
            this.agentId = agentId;
        }
        public void setRating(int rating){
            this.totalRating+=rating;
            this.countOfRating++;
        }

        public double getAverageRating(){
            return this.totalRating/this.countOfRating;
        }

        public int getCountOfRating(){
            return this.countOfRating;
        }

        public int getAgentId(){
            return this.agentId;
        }
        @Override
        public String toString(){
            return "Agent id "+ this.agentId + " has ratings "+ this.getAverageRating()+"\n";
        }

    }
   public static void main(String[] args) {
       //test cases
       //give rating
       AgentScorer agentScorer = new AgentScorer();
       agentScorer.giveRating(1,4);
       agentScorer.giveRating(1,5);
       agentScorer.giveRating(1,3);
       //System.out.println(agentScorer.GetRatings());
       agentScorer.giveRating(2,4);
       agentScorer.giveRating(2,5);
       agentScorer.giveRating(2,2);
       System.out.println(agentScorer.GetRatings());
   }
}

