//Degree Centrality
g.V().group().by(values('first_name')).by(bothE().count())


//Betweeness
g.V().as("v").
           repeat(both().simplePath().as("v")).emit(). 
           filter(project("x","y","z").by(select(first, "v")). 
                                       by(select(last, "v")).
                                       by(select(all, "v").count(local)).as("triple").
                  coalesce(select("x","y").as("a"). 
                             select("triples").unfold().as("t").
                             select("x","y").where(eq("a")).
                             select("t"),
                           store("triples")). 
                  select("z").as("length").
                  select("triple").select("z").where(eq("length"))).
           select(all, "v").unfold(). 
           group().by(valueMap()).by(count()).next()

//Closeness

g.withSack(1f).V().as("v"). 
           repeat(both().simplePath().as("v")).emit().
           filter(project("x","y","z").by(select(first, "v")). 
                                       by(select(last, "v")).
                                       by(select(all, "v").count(local)).as("triple").
                  coalesce(select("x","y").as("a"). 
                             select("triples").unfold().as("t").
                             select("x","y").where(eq("a")).
                             select("t"),
                           store("triples")). 
                  select("z").as("length").
                  select("triple").select("z").where(eq("length"))). 
           group().by(select(first, "v")). 
                   by(select(all, "v").count(local).sack(div).sack().sum()).next()

//Eigenvector

g.V().repeat(groupCount('m').by('first_name').out()).times(5).cap('m'). 
           order(local).by(values, desc).limit(local, 10).next()

//PageRank
g.withComputer().V().pageRank().by('pageRank').valueMap()