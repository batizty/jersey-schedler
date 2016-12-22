package com.weibo.datasys.auth;

import com.weibo.datasys.utils.FileUtils;
import com.weibo.datasys.utils.HadoopPolicySettor;

import javax.servlet.ServletContext;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by tuoyu on 22/12/2016.
 * Hadoop Policy XML
 */

/**
 * Root Resource (exposed at "HadoopAuth" path)
 */
@Path("Auth")
public class HadoopAuth {

    @Context
    private ServletContext context;

    public static final String HDFS_DIR_PRIORITY_SHELL = "add_user_to_hadoop.sh";
    public static final String HADOOP_POLICY_XML_TEMPLATE = "hadoop-policy.xml";

    private static final String user_table_name = "mm_user";
    private static final String group_table_name = "mm_group";

    private String rootPath() {
        return context.getRealPath("");
    }

    private List<String> getUsers() {
        Connection connection = (Connection)context.getAttribute("connection");
        Set<String> users = new HashSet<String>();
        try {
            Statement stat = connection.createStatement();
            String sql = "select userName from " + user_table_name + " where state = 0";
            ResultSet ret = stat.executeQuery(sql);
            while (ret.next()) {
                String u = ret.getString("userName");
                users.add(u);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<String>(users);
    }

    private List<String> getGroups() {
        Connection connection = (Connection)context.getAttribute("connection");
        Set<String> groups = new HashSet<String>();
        try {
            Statement stat = connection.createStatement();
            String sql = "select groupName from " + group_table_name;
            ResultSet ret = stat.executeQuery(sql);
            while (ret.next()) {
                String g = ret.getString("groupName");
                groups.add(g);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<String>(groups);
    }

    /**
     *
     */
    @Path(HADOOP_POLICY_XML_TEMPLATE)
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getXML() {
        List<String> users = getUsers();
        List<String> groups = getGroups();
        return HadoopPolicySettor.getNewHadoopPolicyXML(
                rootPath() + "/" + HADOOP_POLICY_XML_TEMPLATE,
                users,
                groups);
    }

//    @Path(HADOOP_POLICY_XML_TEMPLATE)
//    @POST
//    @Produces(MediaType.TEXT_PLAIN)
//    public String getXML(
//            @FormParam("users") String[] users,
//            @FormParam("groups") String[] groups) {
//        return HadoopPolicySettor.getNewHadoopPolicyXML(
//                rootPath() + "/" + HADOOP_POLICY_XML_TEMPLATE,
//                users,
//                groups);
//    }

    @Path(HDFS_DIR_PRIORITY_SHELL)
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getShell() {
        String content = "";
        try {
            content = FileUtils.readFile(rootPath() + HDFS_DIR_PRIORITY_SHELL);
        } catch (IOException e) {
            System.out.println("error : " + e.getMessage());
            e.printStackTrace();
        }
        return content;
    }

//    public static void main() {
//        HadoopAuth hda = new HadoopAuth();
//        hda.getUsers();
//        return;
//    }
}
