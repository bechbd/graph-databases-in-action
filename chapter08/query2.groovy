// Query 2: What are the ten highest rated restaurants near me?

/** preparation - set input value for uid **/
uid = 8

/** preparation - listing of steps **/
// Start with the current user
// Traverse the lives_in edge to get their city
// Traverse the located_in edge to get the restaurants of the city
// Group by vertex, with average rating
// Order by the average rating, descending
// Limit to the first 10 results


/** step 1 **/
// Start with the current user
g.V().has('user','user_id',uid).
  valueMap().with(WithOptions.tokens)


/** step 2 **/
// Start with the current user
g.V().has('user','user_id',uid).
// Traverse the lives_in edge to get their city
  out('lives_in').
  valueMap().with(WithOptions.tokens)


/** step 3 **/
// Start with the current user
g.V().has('user','user_id',uid).
// Traverse the lives_in edge to get their city
  out('lives_in').
// Traverse the located_in edge to get the restaurants of the city
  in('located_in').
  valueMap().with(WithOptions.tokens)

/** step 4 **/
// Start with the current user
g.V().has('user','user_id',uid).
// Traverse the lives_in edge to get their city
  out('lives_in').
// Traverse the located_in edge to get the restaurants of the city
  in('located_in').
// Group by vertex, with average rating
  group().
    by(identity()).
    by(__.in('about_a').values('rating').mean())


/** step 5 **/
// Start with the current user
g.V().has('user','user_id',uid).
// Traverse the lives_in edge to get their city
  out('lives_in').
// Traverse the located_in edge to get the restaurants of the city
  in('located_in').
// Group by vertex, with average rating
  group().
    by(identity()).
    by(__.in('about_a').values('rating').mean()).
// Order by the average rating, descending
  order(local).
    by(values, desc)


/** step 6 **/
// Start with the current user
g.V().has('user','user_id',uid).
// Traverse the lives_in edge to get their city
  out('lives_in').
// Traverse the located_in edge to get the restaurants of the city
  in('located_in').
// Group by vertex, with average rating
  group().
    by(identity()).
    by(__.in('about_a').values('rating').mean()).
// Order by the average rating, descending
  order(local).
    by(values, desc).
// Limit to the first 10 results
  limit(local,10).
  valueMap().with(WithOptions.tokens)


/** step 7 **/
g.V().has('user','user_id',uid).
// Traverse the lives_in edge to get their city
  out('lives_in').
// Traverse the located_in edge to get the restaurants of the city
  in('located_in').
// Group by vertex, with average rating
  group().
    by(identity()).
    by(__.in('about_a').values('rating').mean()).
// Order by the average rating, descending
  order(local).
    by(values, desc).
// Limit to the first 10 results
  limit(local,10).
// Return restaurants + rating_average
  unfold().
  project('restaurant_id','restaurant_name','address','rating_average').
    by(select(keys).values('restaurant_id')).
    by(select(keys).values('restaurant_name')).
    by(select(keys).values('address')).
    by(select(values))



/** step 1 - code only **/
g.V().has('user','user_id',uid).
  valueMap().with(WithOptions.tokens)

/** step 2 - code only **/
g.V().has('user','user_id',uid).
  out('lives_in').
  valueMap().with(WithOptions.tokens)

/** step 3 - code only **/
g.V().has('user','user_id',uid).
  out('lives_in').
  in('located_in').
  valueMap().with(WithOptions.tokens)

/** step 4 - code only **/
g.V().has('user','user_id',uid).
  out('lives_in').
  in('located_in').
  group().
    by(identity()).
    by(__.in('about_a').values('rating').mean())
  valueMap().with(WithOptions.tokens)

/** step 5 - code only **/
g.V().has('user','user_id',uid).
  out('lives_in').
  in('located_in').
  group().
    by(identity()).
    by(__.in('about_a').values('rating').mean())
  order(local).
    by(values, desc)
  valueMap().with(WithOptions.tokens)

/** step 6 - code only **/
g.V().has('user','user_id',uid).
  out('lives_in').
  in('located_in').
  group().
    by(identity()).
    by(__.in('about_a').values('rating').mean())
  order(local).
    by(values, desc)
  limit(local,10).
  valueMap().with(WithOptions.tokens)

/** step 7 - code only **/
g.V().has('user','user_id',uid).
  out('lives_in').
  in('located_in').
  group().
    by(identity()).
    by(__.in('about_a').values('rating').mean())
  order(local).
    by(values, desc)
  limit(local,10).
  unfold().
  project('restaurant_id','restaurant_name','address','rating_average').
    by(select(keys).values('restaurant_id')).
    by(select(keys).values('restaurant_name')).
    by(select(keys).values('address')).
    by(select(values))

