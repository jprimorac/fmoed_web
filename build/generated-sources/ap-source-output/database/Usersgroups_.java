package database;

import database.Groups;
import database.Users;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2015-09-23T18:34:14")
@StaticMetamodel(Usersgroups.class)
public class Usersgroups_ { 

    public static volatile SingularAttribute<Usersgroups, Integer> id;
    public static volatile SingularAttribute<Usersgroups, Users> user;
    public static volatile SingularAttribute<Usersgroups, Groups> group1;

}