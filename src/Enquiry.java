package src;

public class Enquiry {
    private static int idCounter = 1;
    private int id;
    private String content;
    private Project project;
    private String reply;

    public Enquiry(String content, Project project) {
        this.id = idCounter++;
        this.content = content;
        this.project = project;
        this.reply = "";
    }

    public int getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String newContent) {
        this.content = newContent;
    }

    public Project getProject() {
        return project;
    }

    public String getReply() {
        return reply;
    }

    public void setReply(String reply) {
        this.reply = reply;
    }

    @Override
    public String toString() {
        return "[Enquiry #" + id + "] Project: " + project.getName() + "\nContent: " + content + "\nReply: " + (reply.isEmpty() ? "No reply yet" : reply);
    }
}


