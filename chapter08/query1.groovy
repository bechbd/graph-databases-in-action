// Query 1: What restaurant near me, with a specific cuisine, is the highest rated?

/** preparation - set input value for uid **/
uid = 5
cuisine_list = ['fast food','Mexican']

/** preparation - listing of steps **/
// Start with the current user uid
// Get their current city
// Traverse located_in edge to all restaurants
// Filter for the input cuisine(s)
// compute average rating for each restaurant
// sort by rating_average, descending
// limit to just one response
// create return object = restaurant properties, rating average, cuisine
// return: {restaurant_id=?, restaurant_name=?, address=?, rating_average=?, cuisine=?}

/** final example with code interleaved with preparation comments **/
// Start with the current user uid
g.V().has('user','user_id',uid).
// Get their current city
  out('lives_in').
// Traverse located_in edge to all restaurants
  in('located_in').
// Filter for the input cuisine(s)
  where(out('serves').has('cuisine_name',within(cuisine_list))).
// compute average rating for each restaurant
  group().
    by(identity()).
    by(__.in('about_a').values('rating').mean()).
// sort by rating_average, descending
  order(local).
    by(values, desc).
    unfold().
// limit to just one response
  limit(1).
// create return object = restaurant properties, rating average, cuisine
  project("restaurant_id","restaurant_name","address","rating_average","cuisine").
    by(select(keys).values("restaurant_id")).
    by(select(keys).values("restaurant_name")).
    by(select(keys).values("address")).
    by(select(values)).
    by(select(keys).out('serves').values('cuisine_name'))
// return: {restaurant_id=?, restaurant_name=?, address=?, rating_average=?, cuisine=?}

/** another example with comments indicating output type on the lines of each Gremlin step **/
g.V().has('user','user_id',uid).                                  // user
  out('lives_in').                                                // city
  in('located_in').                                               // restaurant
  where(out('serves').has('cuisine_name',within(cuisine_list))).  // filter restaurant by cuisine
  group().                                                        // group by
    by(identity()).                                               //   key:   restaurant vertex
    by(__.in('about_a').values('rating').mean()).                 //   value: rating_average
  order(local).                                                   // sort collection of k,v
    by(values, desc).                                             //   by rating_average desc
  unfold().                                                       // unfold collection
  limit(1).                                                       // take only the top
  project("restaurant_id","restaurant_name","address","rating_average","cuisine").  // create map
    by(select(keys).values("restaurant_id")).                     // restaurant_id
    by(select(keys).values("restaurant_name")).                   // restaurant_name
    by(select(keys).values("address")).                           // address
    by(select(values)).                                           // rating_average
    by(select(keys).out('serves').values('cuisine_name'))         // cuisine

/** final code-only example  **/
g.V().has('user','user_id',uid).
  out('lives_in').
  in('located_in').
  where(out('serves').has('cuisine_name',within(cuisine_list))).
  group().
    by(identity()).
    by(__.in('about_a').values('rating').mean()).
  order(local).
    by(values, desc).
  unfold().
  limit(1).
  project("restaurant_id","restaurant_name","address","rating_average","cuisine").
    by(select(keys).values("restaurant_id")).
    by(select(keys).values("restaurant_name")).
    by(select(keys).values("address")).
    by(select(values)).
    by(select(keys).out('serves').values('cuisine_name'))

