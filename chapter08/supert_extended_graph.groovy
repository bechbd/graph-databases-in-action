// user (user_id, first_name, last_name)
//   friends user
//   lives_in city
//   wrote_a(review_date) review
//   wrote_a review_rating

// review_rating (rating, rating_date)
//   about_a review

// review (rating, body, created_date)
//   about_a restaurant

// restaurant (restaurant_id, restaurant_name, address)
//   serves cuisine
//   located_in city

// cuisine (cusine_name)

// city (name)
//   located_in state

// state (name)

/**
 *  Build Graph
 */
/** V:state (name) **/
g.addV('state').property('name','Texas').
  iterate()

g.addV('state').property('name','Ohio').
  iterate()

/** V:city (name) **/
g.addV('city').property('name','Houston').
  addE('located_in').to(V().has('state','name','Texas')).
  iterate()

g.addV('city').property('name','Cincinnati').
  addE('located_in').to(V().has('state','name','Ohio')).
  iterate()

/** cuisine (cusine_name) **/
g.addV('cuisine').property('cusine_name','steakhouse').iterate()
g.addV('cuisine').property('cusine_name','salad').iterate()
g.addV('cuisine').property('cusine_name','short order').iterate()
g.addV('cuisine').property('cusine_name','comfort food').iterate()
g.addV('cuisine').property('cusine_name','find dining').iterate()
g.addV('cuisine').property('cusine_name','Mexican').iterate()
g.addV('cuisine').property('cusine_name','Italian').iterate()
g.addV('cuisine').property('cusine_name','Indian').iterate()
g.addV('cuisine').property('cusine_name','Chinese').iterate()
g.addV('cuisine').property('cusine_name','German').iterate()

/**
 * restaurant (restaurant_id, restaurant_name, address)
 *   serves cuisine
 *   located_in city
 */
g.addV('restaurant').as('r')
    property('restaurant_id',1).
    property('restaurant_name','Good Bull').
    property('address','1234 University Dr').
  addE('serves').
    from('r')
    to(V().has('cuisine','cusine_name','steakhouse')).
  addE('located_in').
    from('r').
    to(V().has('city','name','Houston')).
  iterate()

/**
 * user (user_id, first_name, last_name)
 */
g.addV('user').as('u').
    property('user_id',1).
    property('first_name','Dave').
    property('last_name','Josh').
  addE('lives_in').
    from('u').
    to(V().has('city','name','Houston')).
  iterate()



// Add Users (user_id, first_name, last_name)
//   (1, Dave, Bechberger)
//   (2, Josh, Perryman)
//   (3, Ted, Wilson)
//   (4, Hank, Erin)
//   (5, Denise, Green)
//   (6, Kelly, Miller)
//   (7, Jim, )
//   (8, Paras, )


// Add Resturants: (restaurant_id, name, address)
//   Rare Bull - steakhouse
//   Good Bull - roadhouse
//   Rabbitfood - salad / vegan
//   Quick N Greasy - short order
//   Breaded & Fried - comfort food
//   Super Delish - fine dining
//   Eastern Winds - ethnic buffet & take-out
//   Southern Fire - Mexican
//   Northern Quench - bar
//   Western Granola - vegan
//   All Night Long - diner
//   Black Pit of Des Pair - coffee
//   Saucy, Cheesy, Saucers - pizza
//   With Pasta - Italian
//   With Salsa - Mexican
//   With Curry - Indian
//   With Rice&Noodles - Chinese
//   Without heat - Sushi
//   With Ginger - Thai
//   With Beer - German
//   With Wine - French
//   With Shell - Tacos
//   With Sauce - Pizza
//

// Add Cuisine (name)

// start with a given restaurant_id
// travers about_a edge to get to the reviews
// order by created_date desc to get most recent
// limit to 5
// return a map of the review content


restaurant_id = 1
g.V().has('restaurant','restaurant_id',restaurant_id).
  in('about_a').
  order().by('created_date', desc).
  limit(5).
  valueMap('body','rating','created_date')


