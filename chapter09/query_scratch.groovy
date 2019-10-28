g.io("/Users/josh/dev/apache/apache-tinkerpop-gremlin-console-3.4.3/data/sample_ch9.json").
  with(IO.writer,IO.graphson).
  write().iterate()

// Refactor edge label

subgraph = g.V().has('user','user_id',2).bothE('friends').subgraph('sg').cap('sg').next()


subgraph = g.V().has('user','user_id',2).
  bothE('friends').subgraph('e').otherV().
  bothE('friends').subgraph('e').
  cap('e').next()


subgraph = g.V().has('user','user_id',2).as('u').
  bothE('friends').subgraph('e').
  otherV().bothE('friends').
  where(otherV().both('friends').as('u')).subgraph('e').
  cap('e').next()
==>tinkergraph[vertices:4 edges:5]


subgraph = g.V().
  has('user','user_id',2).
  bothE('friends').subgraph('sg').otherV().
  bothE('friends').where(otherV().both('friends').has('user','user_id',2)).subgraph('sg').otherV().
  cap('sg').next()

subgraph = g.V().
  has('user','user_id',2).                         // user
  bothE('friends').subgraph('sg').otherV().       // user (friends)
  outE('wrote').subgraph('sg').inV().           // reviews, review_rating
  optional(outE('about').subgraph('sg').inV()). // reviews from review_rating combined with reviews
  outE('about').subgraph('sg').inV().
  outE('located').subgraph('sg').
  cap('sg').next()


subgraph = g.V().
  has('user','user_id',2).
  bothE('friends').subgraph('sg').otherV().
  outE('wrote').subgraph('sg').inV().
  optional(outE('about').subgraph('sg').inV()).
  outE('about').subgraph('sg').inV().
  outE('located').subgraph('sg').
  cap('sg').next()


subgraph = g.V().
  has('user','user_id',2).
  bothE('friends').subgraph('sg').otherV().as('f').
  union(
      outE('wrote').subgraph('sg').inV(),
      outE('assigned').subgraph('sg').inV().outE('about').subgraph('sg').inV()
  ).
  outE('about').subgraph('sg').inV().
  outE('located').subgraph('sg').
  cap('sg').next()

sg = subgraph.traversal()

sg.V().has('city','name','Houston')

sg.V().has('city','name','Houston').in('within').count()

sg.V().has('city','name','Houston').in('within').valueMap(true)


// subgraph output for client: {vertices:[], edges:[]}
sg.V().fold().
  project('vertices').
    by(__.unfold().valueMap().with(WithOptions.tokens).by(unfold()).fold())

    by(outE().valueMap().with(WithOptions.tokens).fold())

// Only eats at places recommended by his friends

// within a given city, (Cincy) what restruants have been recommended by his friends?

sg.V().limit(10).fold().
  project('vertices','edges').
    by(unfold().valueMap(true).fold()).
    by(unfold().outE().valueMap(true).fold())


sg.V().hasLabel('user').fold()

sg.V().hasLabel('user').fold().
  project('vertices').
   by(unfold().valueMap.with(WithOptions.tokens).fold())

sg.V().hasLabel('user').fold().
  project('vertices').
    by(unfold().valueMap().with(WithOptions.tokens).fold())

sg.V().hasLabel('user').fold().
  project('vertices').
    by(unfold().valueMap().with(WithOptions.tokens).fold())
//{
//  vertices=[
//    {id=3618, label=user, user_id=[1], last_name=[Bech], first_name=[Dave]},
//    {id=3623, label=user, user_id=[2], last_name=[Perry], first_name=[Josh]},
//    {id=3628, label=user, user_id=[3], last_name=[Erin], first_name=[Hank]},
//    {id=3633, label=user, user_id=[4], last_name=[Wilson], first_name=[Ted]}
//  ]
//}

sg.V().hasLabel('user').fold().
  project('vertices').
  by(
    unfold().
    valueMap().with(WithOptions.tokens).
    unfold().
    group().
      by(keys).
      by(select(values).unfold())
  )

sg.V().hasLabel('user').
  fold().
  project('vertices').
    by(unfold().valueMap(true).by(unfold()).fold())
//{
//  vertices=[
//    {id=3618, label=user, user_id=1, last_name=Bech, first_name=Dave},
//    {id=3623, label=user, user_id=2, last_name=Perry, first_name=Josh},
//    {id=3628, label=user, user_id=3, last_name=Erin, first_name=Hank},
//    {id=3633, label=user, user_id=4, last_name=Wilson, first_name=Ted}
//  ]
//}

sg.V().hasLabel('user').
  fold().
  project('vertices','edges').
    by(unfold().valueMap(true).by(unfold()).fold()).
    by(unfold().outE('friends').
       union(
         valueMap(true),
         project('label','inV','inVLabel','outV','outVLabel').
           by(label()).
           by(inV().id()).
           by(inV().label()).
           by(outV().id()).
           by(outV().label())
       ).
      fold()
    ).
  fold()


sg.V().hasLabel('user').
  fold().
  project('vertices','edges').
  by(unfold().valueMap(true).by(unfold()).fold()).
  by(unfold().outE('friends').
    union(
      valueMap(true),
      project('label','inV','inVLabel','outV','outVLabel').
        by(label()).
        by(inV().id()).
        by(inV().label()).
        by(outV().id()).
        by(outV().label())
    ).
    fold()
  ).
  fold()


// https://issues.apache.org/jira/browse/TINKERPOP-2284



unfold().
  group().
  by(keys).
  by(select(values)).

//==>{
//  vertices=[
//    {id=3618, label=user, user_id=1, last_name=Bech, first_name=Dave},
//    {id=3623, label=user, user_id=2, last_name=Perry, first_name=Josh},
//    {id=3628, label=user, user_id=3, last_name=Erin, first_name=Hank},
//    {id=3633, label=user, user_id=4, last_name=Wilson, first_name=Ted}
//  ],
//  edges=[
//    {id=3658, label=friends},
//    {id=3660, label=friends},
//    {id=3661, label=friends}]
//}


/**
 *  Chapter 11
 *  - the schema we wanted at the start
 *  -
 *
 *
 */


sg.E(50).union(valueMap(true),
  project('inV','outV','inVLabel','outVLabel').
    by(inV().id()).
    by(outV().id()).
    by(inV().label()).
    by(outV().label())).unfold().
  group().
    by(keys).
    by(select(values))

sg.V().hasLabel('user').outE('friends').
  union(valueMap(true),
        project('inV','outV','inVLabel','outVLabel').
          by(inV().id()).
          by(outV().id()).
          by(inV().label()).
          by(outV().label())).
  unfold().
  group().
    by(keys).
    by(select(values))



sg.V().hasLabel('user').outE('friends').fold().project('e').by(unfold().fold())

sg.V().hasLabel('user').outE('friends').
  fold().
  project('e').
    by(
      unfold().
      union(
        valueMap(true).by(unfold()).unfold(),
        project('idV').by(inV().id()).unfold()
      ).
      fold()
    )

sg.V().hasLabel('user').
  project('vertices','edges').
  by(unfold().valueMap(true).by(unfold()).fold()).
  by(bothE().otherV().path().
    by(valueMap(true))
  ).
  by(
    union(
      valueMap(true),
      project('inV','outV','inVLabel','outVLabel').
        by(inV().id()).
        by(outV().id()).
        by(inV().label()).
        by(outV().label())
    ).unfold().
      group().
      by(keys).
      by(select(values))
  )

sg.V().hasLabel('user').fold()

sg.V().hasLabel('user').fold().
  project("v").
    by(unfold().valueMap(true).by(unfold()).fold())

sg.V().hasLabel('user').fold().
  project("v").
    by(unfold().valueMap(true).by(unfold()).fold())

sg.V().hasLabel('user').fold().
  project("v","e").
    by(unfold().valueMap(true).by(unfold()).fold()).
    by()


sg.V().hasLabel('user').fold().
  project("v","e").
    by(unfold().valueMap(true).by(unfold()).fold()).
    by(unfold().outE('friends').inV().path().fold())

/**
 {
  v=[
      {id=3840, label=user, user_id=1, last_name=Bech, first_name=Dave},
      {id=3845, label=user, user_id=2, last_name=Perry, first_name=Josh},
      {id=3850, label=user, user_id=3, last_name=Erin, first_name=Hank},
      {id=3855, label=user, user_id=4, last_name=Wilson, first_name=Ted}
   ],
  e=[
      path[[v[3840], v[3845], v[3850], v[3855]], v[3840], e[3880][3840-friends->3845], v[3845]],
      path[[v[3840], v[3845], v[3850], v[3855]], v[3845], e[3882][3845-friends->3850], v[3850]],
      path[[v[3840], v[3845], v[3850], v[3855]], v[3855], e[3883][3855-friends->3845], v[3845]]
    ]
 }
 **/

sg.V().hasLabel('user').fold().
  project("v","e").
  by(unfold().valueMap(true).by(unfold()).fold()).
  by(unfold().as("v1").outE('friends').as("e").inV().as("v2").path().fold())



sg.V().hasLabel('user').fold().
  project("v","e").
    by(unfold().valueMap(true).by(unfold()).fold()).
    by(unfold().as("v1").outE('friends').as("e").inV().as("v2").path().select("e").fold())

/**
 * {
 *  v=[
 *      {id=3840, label=user, user_id=1, last_name=Bech, first_name=Dave},
 *      {id=3845, label=user, user_id=2, last_name=Perry, first_name=Josh},
 *      {id=3850, label=user, user_id=3, last_name=Erin, first_name=Hank},
 *      {id=3855, label=user, user_id=4, last_name=Wilson, first_name=Ted}
 *    ],
 *  e=[
 *    e[3880][3840-friends->3845],
 *    e[3882][3845-friends->3850],
 *    e[3883][3855-friends->3845]
 *    ]
 * }
 *
 */

sg.V().hasLabel('user').fold().
  project("v","e").
    by(unfold().valueMap(true).by(unfold()).fold()).
    by(unfold().as("v1").outE('friends').as("e").inV().as("v2").path().select("v1","e").fold())

/**
 * {v=[{id=3840, label=user, user_id=1, last_name=Bech, first_name=Dave}, {id=3845, label=user, user_id=2, last_name=Perry, first_name=Josh}, {id=3850, label=user, user_id=3, last_name=Erin, first_name=Hank}, {id=3855, label=user, user_id=4, last_name=Wilson, first_name=Ted}],
 *  e=[{v1=v[3840], e=e[3880][3840-friends->3845]}, {v1=v[3845], e=e[3882][3845-friends->3850]}, {v1=v[3855], e=e[3883][3855-friends->3845]}]}
 */

sg.V().hasLabel('user').fold().
  project("v","e").
    by(unfold().valueMap(true).by(unfold()).fold()).
    by(unfold().as("v1").outE('friends').as("e").inV().as("v2").path().
      union(
        select("e").valueMap(),
        project("inV","inVLabel","outV","outVLabel").
          by(select("v1").id()).
          by(select("v1").label()).
          by(select("v2").id()).
          by(select("v2").label())
      ).
      unfold().
      group().
        by(keys).
        by(select(values)).
      fold())

sg.V().hasLabel('user').fold().
  project("v","e").
  by(unfold().valueMap(true).by(unfold()).fold()).
  by(unfold().as("v1").outE('friends').as("e").inV().as("v2").path().
    union(
      select("e").valueMap(true),
      project("inV","inVLabel","outV","outVLabel").
        by(select("v1").id()).
        by(select("v1").label()).
        by(select("v2").id()).
        by(select("v2").label())
    ).fold())

/**
 * {v=[{id=7512, label=user, user_id=1, last_name=Bech, first_name=Dave}, {id=7517, label=user, user_id=2, last_name=Perry, first_name=Josh}, {id=7522, label=user, user_id=3, last_name=Erin, first_name=Hank}, {id=7527, label=user, user_id=4, last_name=Wilson, first_name=Ted}],
 *  e=[{id=7552, label=friends, created_date=Sat Oct 17 12:50:53 CDT 2015}, {inV=7512, inVLabel=user, outV=7517, outVLabel=user}, {id=7554, label=friends, created_date=Sat Dec 30 17:33:31 CST 2017}, {inV=7517, inVLabel=user, outV=7522, outVLabel=user}, {id=7555, label=friends, created_date=Tue Sep 08 11:14:22 CDT 2015}, {inV=7527, inVLabel=user, outV=7517, outVLabel=user}]}
 **/

sg.V().hasLabel('user').fold().
  project("v","e").
  by(unfold().valueMap(true).by(unfold()).fold()).
  by(unfold().as("v1").outE('friends').as("e").inV().as("v2").path().
    union(
      select("e").valueMap(true),
      project("inV","inVLabel","outV","outVLabel").
        by(select("v1").id()).
        by(select("v1").label()).
        by(select("v2").id()).
        by(select("v2").label())
    ).fold())



sg.V().hasLabel('user').
  project('vertices','edges').
    by(unfold().valueMap(true).by(unfold()).fold()).
    by(bothE('friends').as('e').otherV().path().
      select('e').
      union(
        valueMap(true),
        project('inV','outV','inVLabel','outVLabel').
          by(inV().id()).
          by(outV().id()).
          by(inV().label()).
          by(outV().label())
      ).unfold().
      group().
        by(keys).
        by(select(values))
    )
/**
 * ==>{vertices=[{id=7512, label=user, user_id=1, last_name=Bech, first_name=Dave}], edges={id=7552, label=friends, inV=7517, inVLabel=user, outVLabel=user, created_date=Sat Oct 17 12:50:53 CDT 2015, outV=7512}}
 * ==>{vertices=[{id=7517, label=user, user_id=2, last_name=Perry, first_name=Josh}], edges={id=7555, label=friends, inV=7517, inVLabel=user, outVLabel=user, created_date=Tue Sep 08 11:14:22 CDT 2015, outV=7527}}
 * ==>{vertices=[{id=7522, label=user, user_id=3, last_name=Erin, first_name=Hank}], edges={id=7554, label=friends, inV=7522, inVLabel=user, outVLabel=user, created_date=Sat Dec 30 17:33:31 CST 2017, outV=7517}}
 * ==>{vertices=[{id=7527, label=user, user_id=4, last_name=Wilson, first_name=Ted}], edges={id=7555, label=friends, inV=7517, inVLabel=user, outVLabel=user, created_date=Tue Sep 08 11:14:22 CDT 2015, outV=7527}}
 */

sg.V().hasLabel('user').
  project('v','e').
  by(unfold().valueMap(true).by(unfold()).fold()).
  by(bothE('friends').as('e').otherV().path().
    select('e').
    union(
      valueMap(true),
      project('inV','outV','inVLabel','outVLabel').
        by(inV().id()).
        by(outV().id()).
        by(inV().label()).
        by(outV().label())
    ).unfold().
    group().
    by(keys).
    by(select(values))
  ).fold().
  project("vertices","edges").
    by(unfold().select("v").dedup().fold()).
    by(unfold().select("e").dedup().fold())

/**
 * {
 *   vertices=[
 *      [{id=7512, label=user, user_id=1, last_name=Bech, first_name=Dave}],
 *      [{id=7517, label=user, user_id=2, last_name=Perry, first_name=Josh}],
 *      [{id=7522, label=user, user_id=3, last_name=Erin, first_name=Hank}],
 *      [{id=7527, label=user, user_id=4, last_name=Wilson, first_name=Ted}]],
 *   edges=[
 *     {id=7552, label=friends, inV=7517, inVLabel=user, outVLabel=user, created_date=Sat Oct 17 12:50:53 CDT 2015, outV=7512},
 *     {id=7555, label=friends, inV=7517, inVLabel=user, outVLabel=user, created_date=Tue Sep 08 11:14:22 CDT 2015, outV=7527},
 *     {id=7554, label=friends, inV=7522, inVLabel=user, outVLabel=user, created_date=Sat Dec 30 17:33:31 CST 2017, outV=7517}]}
 **/

sg.V().
  project('v','e').
    by(unfold().valueMap(true).by(unfold())).
    by(bothE().as('e').otherV().path().
      select('e').
      union(
        valueMap(true),
        project('inV','outV','inVLabel','outVLabel').
          by(inV().id()).
          by(outV().id()).
          by(inV().label()).
          by(outV().label())
      ).unfold().
      group().
        by(keys).
        by(select(values))
    ).fold().
  project("vertices","edges").
    by(unfold().select("v").dedup().fold()).
    by(unfold().select("e").dedup().fold())



sg.V().hasLabel('user').
  project('v','e').
    by(unfold().valueMap(true).by(unfold())).
    by(
      bothE('friends').
      union(
        valueMap(true),
        project('inV','outV','inVLabel','outVLabel').
          by(inV().id()).
          by(outV().id()).
          by(inV().label()).
          by(outV().label())
      ).
      unfold().
      group().
        by(keys).
        by(select(values))
    ).
  fold().
  project("vertices","edges").
    by(unfold().select("v").dedup().fold()).
    by(unfold().select("e").dedup().fold())

//  {
//    vertices=[
//      {id=7512, label=user, user_id=1, last_name=Bech, first_name=Dave},
//      {id=7517, label=user, user_id=2, last_name=Perry, first_name=Josh},
//      {id=7522, label=user, user_id=3, last_name=Erin, first_name=Hank},
//      {id=7527, label=user, user_id=4, last_name=Wilson, first_name=Ted}
//    ]
//  , edges=[
//      {id=7552, label=friends, inV=7517, inVLabel=user, outVLabel=user, created_date=Sat Oct 17 12:50:53 CDT 2015, outV=7512},
//      {id=7555, label=friends, inV=7517, inVLabel=user, outVLabel=user, created_date=Tue Sep 08 11:14:22 CDT 2015, outV=7527},
//      {id=7554, label=friends, inV=7522, inVLabel=user, outVLabel=user, created_date=Sat Dec 30 17:33:31 CST 2017, outV=7517}
//    ]
// }


sg.V().
  project('v','e').
  by(unfold().valueMap(true).by(unfold())).
  by(
    bothE().
      union(
        valueMap(true),
        project('inV','outV','inVLabel','outVLabel').
          by(inV().id()).
          by(outV().id()).
          by(inV().label()).
          by(outV().label())
      ).
      unfold().
      group().
        by(keys).
        by(select(values))
  ).
  fold().
  project("vertices","edges").
    by(unfold().select("v").dedup().fold()).
    by(unfold().select("e").dedup().fold())

// Based on my friends’ review ratings, what are the best restaurants for me in this area?

city_name = 'Houston'

sg.V().has('city','name',city_name).
  in('located').as("restaurant").
  in('about').as("review").
  select("restaurant","review").
  group().
    by(select("restaurant").values("restaurant_name")).
    by(select("review").values("rating").mean()).
  order().by(value)





g.V(1).as('a').out('created').in('created').where(neq('a')) //1\
g.withSideEffect('a',['josh','peter']).V(1).out('created').in('created').values('name').where(within('a')) //2\
g.V(1).out('created').in('created').where(out('created').count().is(gt(1))).values('name')


g.V().has('user','user_id',2).
  sideEffect(both('friends').store('v')).
  cap('v').
  bothE('friends').as('e').otherV().
  bothE('friends').where(otherV().is(within(select('v').select(keys)))).as('e').
  select('v')


g.V().has('user','user_id',2).
  sideEffect(both('friends').store('v')).
  cap('v').
  bothE('friends').as('e').otherV().
  bothE('friends').where(otherV().is(within(select('v').keys()))).as('e').
  select('v')


g.V().has('user','user_id',2).as('u').
  bothE('friends').as('e').otherV().
  bothE('friends').where(bothV().both('friends').as('u')).as('e').
  select('e').dedup()

subgraph2 = g.V().has('user','user_id',2).as('u').
  bothE('friends').subgraph('e').otherV().
  bothE('friends').where(otherV().both('friends').as('u')).subgraph('e').
  cap('e').next()

subgraph = g.V().has('user','user_id',2).as('u').
  bothE('friends').subgraph('e').otherV().
  bothE('friends').where(otherV().both('friends').as('u')).subgraph('e').
  cap('e').next()


//
subgraph = g.V().
  has('user','user_id',2).
  bothE('friends').subgraph('sg').otherV().
  outE('wrote').subgraph('sg').inV().
  optional(outE('about').subgraph('sg').inV()).
  outE('about').subgraph('sg').inV().
  outE('located').subgraph('sg').
  cap('sg').next()

sg = subgraph.traversal()

city_name = 'Houston'

sg.V().hasLabel('review').
  out('about').
  where(out('located').has('city','name',city_name)).count()

sg.V().hasLabel('review').
  out('about').
  where(out('located').has('city','name','Cincinnati')).count()

sg.V().hasLabel('review').
  out('about').
  where(out('located').has('city','name',city_name)).
  group().
    by(identity()).
    by(__.in('about').values('rating').mean())

sg.V().hasLabel('review').
  out('about').
  where(out('located').has('city','name',city_name)).
  group().
    by(identity()).
    by(__.in('about').values('rating').mean()).
  order(local).
    by(values, desc).
  limit(local,10).
  unfold().
  project('restaurant_id','restaurant_name','address','rating_average').
    by(select(keys).values('restaurant_id')).
    by(select(keys).values('restaurant_name')).
    by(select(keys).values('address')).
    by(select(values))





sg.V().has('city','name',city_name).
  in('located').
  group().
    by(identity()).
    by(__.in('about').values('rating').mean()).
  order(local).
    by(values, desc).
  limit(local,10).
  unfold().
  project('restaurant_id','restaurant_name','address','rating_average').
    by(select(keys).values('restaurant_id')).
    by(select(keys).values('restaurant_name')).
    by(select(keys).values('address')).
    by(select(values))



sg.V().hasLabel('review').
  out('about').
  where(out('located').has('city','name',city_name)).
  group().
  by(identity()).
  by(__.in('about').values('rating').mean()).
  order(local).
  by(values, desc).
  limit(local,10).
  unfold().
  project('restaurant_id','restaurant_name','address','rating_average').
  by(select(keys).values('restaurant_id')).
  by(select(keys).values('restaurant_name')).
  by(select(keys).values('address')).
  by(select(values)).
  profile()


sg.V().has('city','name',city_name).
  in('located').
  group().
  by(identity()).
  by(__.in('about').values('rating').mean()).
  order(local).
  by(values, desc).
  limit(local,10).
  unfold().
  project('restaurant_id','restaurant_name','address','rating_average').
  by(select(keys).values('restaurant_id')).
  by(select(keys).values('restaurant_name')).
  by(select(keys).values('address')).
  by(select(values)).
  profile()




// streaming graph
sg.V().hasLabel('user').
  project('v','e').
    by(unfold().valueMap(true).by(unfold())).
    by(
      bothE('friends').
        union(
          valueMap(true),
          project('inV','outV','inVLabel','outVLabel').
            by(inV().id()).
            by(outV().id()).
            by(inV().label()).
            by(outV().label())
        ).
        unfold().
        group().
          by(keys).
          by(select(values))
    ).
    fold().
    project("vertices","edges").
      by(unfold().select("v").dedup().fold()).
      by(unfold().select("e").dedup().fold())

