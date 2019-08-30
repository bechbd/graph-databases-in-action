// Query 3: What are the newest reviews for this restaurant?

/** preparation - set input value for rid **/
rid = 31  // Dave's Big Deluxe

/** preparation - listing of steps **/
// Get the restaurant based on the restaurant_id input
// Traverse the about_a edges to the reviews
// Order reviews by created_date in descending order
// Limit the results to the top 3
// Return the reviews

/** step 1 **/
// Get the restaurant based on the restaurant_id input
g.V().has('restaurant','restaurant_id',rid).
  valueMap()


/** step 2 **/
// Get the restaurant based on the restaurant_id input
g.V().has('restaurant','restaurant_id',rid).
// Traverse the about_a edges to the reviews
  in('about_a').
  valueMap()


/** step 3 **/
// Get the restaurant based on the restaurant_id input
g.V().has('restaurant','restaurant_id',rid).
// Traverse the about_a edges to the reviews
  in('about_a').
// Order reviews by created_date in descending order
  order().by('created_date',desc).
  valueMap('rating','created_date')


/** step 4 **/
// Get the restaurant based on the restaurant_id input
g.V().has('restaurant','restaurant_id',rid).
// Traverse the about_a edges to the reviews
  in('about_a').
// Order reviews by created_date in descending order
  order().by('created_date',desc).
// Limit the results to the top 3
  limit(3).
  valueMap('rating','created_date')


/** step 5 **/
// Get the restaurant based on the restaurant_id input
  g.V().has('restaurant','restaurant_id',rid).
// Traverse the about_a edges to the reviews
  in('about_a').
// Order reviews by created_date in descending order
  order().by('created_date',desc).
// Limit the results to the top 3
  limit(3).
// Return the reviews
  valueMap('rating','created_date','body').
    with(WithOptions.tokens)


/** final query without comments **/
// Query 3: What are the newest reviews for this restaurant?
g.V().has('restaurant','restaurant_id',rid).
  in('about_a').
  order().by('created_date',desc).
  limit(3).
  valueMap('rating','created_date','body').
    with(WithOptions.tokens)


/** Examples using the range() step **/
g.V().has('restaurant','restaurant_id',rid).
  in('about_a').
  order().by('created_date',desc).
  range(0,3).
  valueMap('rating','created_date','body').
    with(WithOptions.tokens)

g.V().has('restaurant','restaurant_id',rid).
  in('about_a').
  order().by('created_date',desc).
  range(3,6).
  valueMap('rating','created_date','body').
    with(WithOptions.tokens)

g.V().has('restaurant','restaurant_id',rid).
  in('about_a').
  order().by('created_date',desc).
  range(6,9).
  valueMap('rating','created_date','body').
    with(WithOptions.tokens)

