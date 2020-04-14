//Degree Centrality
g.V().hasLabel('person').group().by(values('first_name')).by(bothE('friends').count())

//Betweeness Centrality
g.V().hasLabel('person').as("v").
           repeat(both('friends').simplePath().as("v")).emit(). 
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

//Closeness Centrality
g.withSack(1f).V().hasLabel('person').as("v"). 
           repeat(both('friends').simplePath().as("v")).emit().
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
           group().by(select(first, "v").values('first_name')). 
                   by(select(all, "v").count(local).sack(div).sack().sum()).next()


//Eigenvector Centrality
g.V().hasLabel('person').repeat(groupCount('m').by('first_name').out('friends')).times(5).cap('m'). 
           order(local).by(values, desc).limit(local, 10).next()

//PageRank
g.withComputer().V().pageRank().by('pageRank').valueMap()