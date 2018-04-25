package ie.gasgit.helpdesk;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;


// class to convert and return POJO to JsonObject in format
// required by zen api


public class ConvertToJSON {

    public  String toJSON(Ticket ticket){

        try {
            JsonObject jsonParams = new JsonObject();

            JsonObject request = new JsonObject();

            jsonParams.add("request", request);

            JsonObject requester = new JsonObject();
            requester.addProperty("name", ticket.getName());

            request.add("requester", requester);

            request.addProperty("subject", ticket.getSubject());

            JsonArray customFields = new JsonArray();
            request.add("custom_fields",customFields);


            JsonObject c_fields1 = new JsonObject();

            c_fields1.addProperty("id", "360000030009");
            c_fields1.addProperty("value", ticket.getContact());
            customFields.add(c_fields1);

            JsonObject c_fields2 = new JsonObject();

            c_fields2.addProperty("id", "360000028405");
            c_fields2.addProperty("value", ticket.getEmp_id());
            customFields.add(c_fields2);

            JsonObject c_fields3 = new JsonObject();

            c_fields3.addProperty("id", "360000032529");
            c_fields3.addProperty("value", ticket.getSupport_type());
            customFields.add(c_fields3);

            JsonObject c_fields4 = new JsonObject();

            c_fields4.addProperty("id", "360000027785");
            c_fields4.addProperty("value", ticket.getSchool());
            customFields.add(c_fields4);


            JsonObject comment = new JsonObject();
            request.add("comment", comment);
            comment.addProperty("body", ticket.getDetailed_message()
            );


//            JsonArray attachments = new JsonArray();
//            comment.add("attachments", attachments);
//
//            JsonObject att = new JsonObject();
//
//            att.addProperty("file_name", "file upload");
//            att.addProperty("url", "FOLDER");
//            att.addProperty("content_type", "content");
//            attachments.add(att);

            return jsonParams.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }


        return null;
    }
}
