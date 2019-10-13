subgraph = g.V().
  has('user','user_id',2).
  bothE('friends').subgraph('sg').otherV().
  outE('wrote').subgraph('sg').inV().
  optional(outE('about').subgraph('sg').inV()).
  outE('about').subgraph('sg').inV().
  outE('located').subgraph('sg').
  cap('sg').next()

sg = subgraph.traversal()

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


sg.V().hasLabel('user').fold()



subgraph2 = g.V().
  has('user','user_id',2).
  bothE('friends').subgraph('sg').otherV().
  outE('wrote').subgraph('sg').inV().
  optional(outE('about').subgraph('sg').inV()).
  outE('about').subgraph('sg').inV().
  outE('located').subgraph('sg').
  cap('sg').next()

sg2 = subgraph2.traversal()

sg2.V().has('city','name',city_name).
  in('located').
  group().
  by(identity()).
  by(__.in('about').values('rating').mean()).
  order(local).
  by(values, desc).
  limit(local,3).
  unfold().
  project('restaurant_id','restaurant_name','address','rating_average').
  by(select(keys).values('restaurant_id')).
  by(select(keys).values('restaurant_name')).
  by(select(keys).values('address')).
  by(select(values))


subgraph8 = g.V().
  has('user','user_id',8).
  bothE('friends').subgraph('sg').otherV().
  outE('wrote').subgraph('sg').inV().
  optional(outE('about').subgraph('sg').inV()).
  outE('about').subgraph('sg').inV().
  outE('located').subgraph('sg').
  cap('sg').next()

sg8 = subgraph8.traversal()

sg8.V().has('city','name',city_name).
  in('located').
  group().
  by(identity()).
  by(__.in('about').values('rating').mean()).
  order(local).
  by(values, desc).
  limit(local,3).
  unfold().
  project('restaurant_id','restaurant_name','address','rating_average').
  by(select(keys).values('restaurant_id')).
  by(select(keys).values('restaurant_name')).
  by(select(keys).values('address')).
  by(select(values))



subgraph7 = g.V().
  has('user','user_id',7).
  bothE('friends').subgraph('sg').otherV().
  outE('wrote').subgraph('sg').inV().
  optional(outE('about').subgraph('sg').inV()).
  outE('about').subgraph('sg').inV().
  outE('located').subgraph('sg').
  cap('sg').next()

sg7 = subgraph7.traversal()

sg7.V().has('city','name',city_name).
  in('located').
  group().
  by(identity()).
  by(__.in('about').values('rating').mean()).
  order(local).
  by(values, desc).
  limit(local,3).
  unfold().
  project('restaurant_id','restaurant_name','address','rating_average').
  by(select(keys).values('restaurant_id')).
  by(select(keys).values('restaurant_name')).
  by(select(keys).values('address')).
  by(select(values))
