package src;

/**
 * Represents an enquiry submitted by an applicant regarding a specific project.
 * Each enquiry is assigned a unique ID and can contain a reply.
 */
public class Enquiry {
    private static int idCounter = 1;
    private int id;
    private String content;
    private Project project;
    private String reply;

    /**
     * Constructs a new Enquiry object.
     *
     * @param content the enquiry message content
     * @param project the project this enquiry is associated with
     */
    public Enquiry(String content, Project project) {
        this.id = idCounter++;
        this.content = content;
        this.project = project;
        this.reply = "";
    }

    /**
     * Returns the unique ID of the enquiry.
     *
     * @return the enquiry ID
     */
    public int getId() {
        return id;
    }

    /**
     * Returns the enquiry content/message.
     *
     * @return the enquiry content
     */
    public String getContent() {
        return content;
    }

    /**
     * Updates the content/message of the enquiry.
     *
     * @param newContent the new enquiry message
     */
    public void setContent(String newContent) {
        this.content = newContent;
    }

    /**
     * Returns the project associated with the enquiry.
     *
     * @return the project
     */
    public Project getProject() {
        return project;
    }

    /**
     * Returns the reply to the enquiry.
     *
     * @return the reply string
     */
    public String getReply() {
        return reply;
    }

    /**
     * Sets the reply for the enquiry.
     *
     * @param reply the reply content
     */
    public void setReply(String reply) {
        this.reply = reply;
    }

    /**
     * Returns a string representation of the enquiry, including project name, content, and reply.
     *
     * @return formatted enquiry details
     */
    @Override
    public String toString() {
        return "[Enquiry #" + id + "] Project: " + project.getName() + "\nContent: " + content + "\nReply: " + (reply.isEmpty() ? "No reply yet" : reply);
    }
}
