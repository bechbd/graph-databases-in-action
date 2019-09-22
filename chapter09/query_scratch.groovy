// Refactor edge label

subgraph = g.V().has('user','user_id',2).bothE('is_friends_with').subgraph('sg').cap('sg').next()


subgraph = g.V().
  has('user','user_id',2).
  bothE('is_friends_with').subgraph('sg').otherV().
  bothE('is_friends_with').where(otherV().both('is_friends_with').has('user','user_id',2)).subgraph('sg').otherV().
  cap('sg').next()

subgraph = g.V().
  has('user','user_id',2).                         // user
  bothE('friends').subgraph('sg').otherV().       // user (friends)
  outE('wrote_a').subgraph('sg').inV().           // reviews, review_rating
  optional(outE('about_a').subgraph('sg').inV()). // reviews from review_rating combined with reviews
  outE('about_a').subgraph('sg').inV().
  outE('located_in').subgraph('sg').
  cap('sg').next()


subgraph = g.V().
  has('user','user_id',2).
  bothE('is_friends_with').subgraph('sg').otherV().
  outE('wrote_a').subgraph('sg').inV().
  optional(outE('about_a').subgraph('sg').inV()).
  outE('about_a').subgraph('sg').inV().
  outE('located_in').subgraph('sg').
  cap('sg').next()


subgraph = g.V().
  has('user','user_id',2).
  bothE('friends').subgraph('sg').otherV().as('f').
  union(
      outE('wrote').subgraph('sg').inV(),
      outE('assigned').subgraph('sg').inV().outE('about_a').subgraph('sg').inV()
  ).
  outE('about_a').subgraph('sg').inV().
  outE('located_in').subgraph('sg').
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


/**
 *  Chapter 11
 *  - the schema we wanted at the start
 *  -
 *
 *
 */
