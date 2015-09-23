package database;

import database.Usergroupreview;
import database.Usersgroups;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2015-09-23T18:34:14")
@StaticMetamodel(Users.class)
public class Users_ { 

    public static volatile ListAttribute<Users, Usersgroups> usersgroupsList;
    public static volatile SingularAttribute<Users, String> password;
    public static volatile SingularAttribute<Users, String> surname;
    public static volatile SingularAttribute<Users, String> name;
    public static volatile ListAttribute<Users, Usergroupreview> usergroupreviewList;
    public static volatile SingularAttribute<Users, Integer> id;
    public static volatile SingularAttribute<Users, String> username;

}