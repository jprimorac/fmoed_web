package database;

import database.Projects;
import database.Usergroupreview;
import database.Usersgroups;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2015-09-23T18:34:14")
@StaticMetamodel(Groups.class)
public class Groups_ { 

    public static volatile ListAttribute<Groups, Usersgroups> usersgroupsList;
    public static volatile SingularAttribute<Groups, String> name;
    public static volatile ListAttribute<Groups, Usergroupreview> usergroupreviewList;
    public static volatile SingularAttribute<Groups, Projects> project;
    public static volatile SingularAttribute<Groups, Integer> id;

}