package database;

import database.Groups;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2015-09-23T18:34:14")
@StaticMetamodel(Projects.class)
public class Projects_ { 

    public static volatile SingularAttribute<Projects, String> name;
    public static volatile SingularAttribute<Projects, String> description;
    public static volatile ListAttribute<Projects, Groups> groupsList;
    public static volatile SingularAttribute<Projects, Integer> id;
    public static volatile SingularAttribute<Projects, String> imageLocation;

}