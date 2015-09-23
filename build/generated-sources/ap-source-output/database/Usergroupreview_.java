package database;

import database.Groups;
import database.Reviews;
import database.Users;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2015-09-23T18:34:14")
@StaticMetamodel(Usergroupreview.class)
public class Usergroupreview_ { 

    public static volatile SingularAttribute<Usergroupreview, Reviews> review;
    public static volatile SingularAttribute<Usergroupreview, Integer> id;
    public static volatile SingularAttribute<Usergroupreview, Users> user;
    public static volatile SingularAttribute<Usergroupreview, Groups> group1;

}