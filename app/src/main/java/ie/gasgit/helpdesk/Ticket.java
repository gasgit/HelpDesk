package ie.gasgit.helpdesk;

/**
 * Created by ubuntu on 19/04/2018.
 */

public class Ticket {

    private String name;
    private String subject;
    private String email;
    private String school;
    private String contact;
    private String emp_id;
    private String support_type;
    private String detailed_message;

    public Ticket(String name, String subject, String email, String school, String contact, String emp_id, String support_type, String detailed_message) {
        this.name = name;
        this.subject = subject;
        this.email = email;
        this.school = school;
        this.contact = contact;
        this.emp_id = emp_id;
        this.support_type = support_type;
        this.detailed_message = detailed_message;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getEmp_id() {
        return emp_id;
    }

    public void setEmp_id(String emp_id) {
        this.emp_id = emp_id;
    }

    public String getSupport_type() {
        return support_type;
    }

    public void setSupport_type(String support_type) {
        this.support_type = support_type;
    }

    public String getDetailed_message() {
        return detailed_message;
    }

    public void setDetailed_message(String detailed_message) {
        this.detailed_message = detailed_message;
    }
}
