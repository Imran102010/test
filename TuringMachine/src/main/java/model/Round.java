package model;

public class Round {

    private int score;
    private Code codeProposal;
    private boolean roundStarted;

    /**
     * Constructs a new round with an initial score of 0 and the round not started.
     */
    public Round(){
        score=0;
        roundStarted = false;
    }

    /**
     * Sets the proposed code for the round and marks the round as started.
     *
     * @param codeProposal The proposed code for the round.
     */
    public void setProposedCode (Code codeProposal){
        this.codeProposal = codeProposal;
        roundStarted = true;
    }

    /**
     * Unsets the proposed code for the round and marks the round as not started.
     */
    public void unSetProposedCode(){
        codeProposal = null;
        roundStarted = false;
    }

    /**
     * Increments the score for the round.
     */
    public void incrementScore(){
        score++;
    }

    /**
     * Decrements the score for the round.
     */
    public void decrementScore(){
        score--;
    }

    /**
     * Gets the current score for the round.
     *
     * @return The current score for the round.
     */
    public int getScore() {
        return score;
    }

    /**
     * Gets a copy of the proposed code for the round.
     *
     * @return A copy of the proposed code for the round.
     */
    public Code getCodeProposal() {
        if (codeProposal==null) {
            return null;
        }
        return new Code(codeProposal);
    }

    /**
     * Checks if the round is started.
     *
     * @return `true` if the round is started, otherwise `false`.
     */
    public boolean isRoundStarted() {
        return !roundStarted;
    }
}
