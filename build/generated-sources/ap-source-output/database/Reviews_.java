package database;

import database.Files;
import database.Usergroupreview;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2015-09-23T18:34:14")
@StaticMetamodel(Reviews.class)
public class Reviews_ { 

    public static volatile SingularAttribute<Reviews, Integer> grade;
    public static volatile ListAttribute<Reviews, Files> filesList;
    public static volatile SingularAttribute<Reviews, String> name;
    public static volatile ListAttribute<Reviews, Usergroupreview> usergroupreviewList;
    public static volatile SingularAttribute<Reviews, String> comment;
    public static volatile SingularAttribute<Reviews, Integer> id;
    public static volatile SingularAttribute<Reviews, Integer> type;

}