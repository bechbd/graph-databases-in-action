// Query 1: What restaurant near me, with a specific cuisine, is the highest rated?

/** preparation - set input value for uid **/
uid = 5
cuisine_list = ['fast food','Mexican']

// Original query
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
  project('restaurant_id','restaurant_name','address','rating_average','cuisine').
    by(select(keys).values('restaurant_id')).
    by(select(keys).values('restaurant_name')).
    by(select(keys).values('address')).
    by(select(values)).
    by(select(keys).out('serves').values('cuisine_name'))

// With profile()
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
  project('restaurant_id','restaurant_name','address','rating_average','cuisine').
    by(select(keys).values('restaurant_id')).
    by(select(keys).values('restaurant_name')).
    by(select(keys).values('address')).
    by(select(values)).
    by(select(keys).out('serves').values('cuisine_name')).
  profile()

// v1: original query and schema
// TEST 1 of v1 (cold): 84.773ms
/**
 ==>Traversal Metrics
 Step                                                               Count  Traversers       Time (ms)    % Dur
 =============================================================================================================
 TinkerGraphStep(vertex,[~label.eq(user), user_i...                     1           1          24.906    29.38
 VertexStep(OUT,[lives_in],vertex)                                      1           1           0.848     1.00
 VertexStep(IN,[located_in],vertex)                                    14          14           1.095     1.29
 NoOpBarrierStep(2500)                                                 14          14           0.692     0.82
 TraversalFilterStep([VertexStep(OUT,[serves],ve...                     4           4           4.042     4.77
   VertexStep(OUT,[serves],vertex)                                     14          14           0.507
   HasStep([cuisine_name.within([fast food, Mexi...                                             0.585
 GroupStep([IdentityStep, ProfileStep],[VertexSt...                     1           1           5.767     6.80
   IdentityStep                                                         4           4           0.072
   VertexStep(IN,[about_a],vertex)                                     12          12           0.339
   PropertiesStep([rating],value)                                      12          12           1.834
   MeanGlobalStep                                                       4           4           1.640
 OrderLocalStep([[values, desc]])                                       1           1          44.654    52.68
 UnfoldStep                                                             2           2           0.134     0.16
 RangeGlobalStep(0,1)                                                   1           1           0.174     0.21
 ProjectStep([restaurant_id, restaurant_name, ad...                     1           1           1.042     1.23
   TraversalMapStep(keys)                                               1           1           0.045
   PropertiesStep([restaurant_id],value)                                1           1           0.111
   TraversalMapStep(keys)                                               1           1           0.016
   PropertiesStep([restaurant_name],value)                              1           1           0.040
   TraversalMapStep(keys)                                               1           1           0.014
   PropertiesStep([address],value)                                      1           1           0.049
   TraversalMapStep(values)                                             1           1           0.022
   TraversalMapStep(keys)                                               1           1           0.016
   VertexStep(OUT,[serves],vertex)                                      1           1           0.058
   PropertiesStep([cuisine_name],value)                                 1           1           0.049
 ReferenceElementStep                                                   1           1           1.414     1.67
 >TOTAL                     -           -          84.773        -
**/

// TEST 2 of v1 (warm) 10.443ms
/**
 ==>Traversal Metrics
 Step                                                               Count  Traversers       Time (ms)    % Dur
 =============================================================================================================
 TinkerGraphStep(vertex,[~label.eq(user), user_i...                     1           1           1.114    10.67
 VertexStep(OUT,[lives_in],vertex)                                      1           1           0.113     1.08
 VertexStep(IN,[located_in],vertex)                                    14          14           0.193     1.85
 NoOpBarrierStep(2500)                                                 14          14           2.964    28.38
 TraversalFilterStep([VertexStep(OUT,[serves],ve...                     4           4           1.637    15.68
   VertexStep(OUT,[serves],vertex)                                     14          14           0.511
   HasStep([cuisine_name.within([fast food, Mexi...                                             0.383
 GroupStep([IdentityStep, ProfileStep],[VertexSt...                     1           1           2.222    21.28
   IdentityStep                                                         4           4           0.043
   VertexStep(IN,[about_a],vertex)                                     12          12           0.312
   PropertiesStep([rating],value)                                      12          12           0.505
   MeanGlobalStep                                                       4           4           0.646
 OrderLocalStep([[values, desc]])                                       1           1           0.613     5.88
 UnfoldStep                                                             2           2           0.066     0.64
 RangeGlobalStep(0,1)                                                   1           1           0.129     1.24
 ProjectStep([restaurant_id, restaurant_name, ad...                     1           1           0.886     8.49
   TraversalMapStep(keys)                                               1           1           0.022
   PropertiesStep([restaurant_id],value)                                1           1           0.057
   TraversalMapStep(keys)                                               1           1           0.018
   PropertiesStep([restaurant_name],value)                              1           1           0.044
   TraversalMapStep(keys)                                               1           1           0.026
   PropertiesStep([address],value)                                      1           1           0.051
   TraversalMapStep(values)                                             1           1           0.024
   TraversalMapStep(keys)                                               1           1           0.017
   VertexStep(OUT,[serves],vertex)                                      1           1           0.056
   PropertiesStep([cuisine_name],value)                                 1           1           0.051
 ReferenceElementStep                                                   1           1           0.503     4.82
 >TOTAL                     -           -          10.443        -
**/

// TEST 3 of v1 (warm) 9.411ms
/**
 ==>Traversal Metrics
 Step                                                               Count  Traversers       Time (ms)    % Dur
 =============================================================================================================
 TinkerGraphStep(vertex,[~label.eq(user), user_i...                     1           1           1.084    11.52
 VertexStep(OUT,[lives_in],vertex)                                      1           1           0.110     1.17
 VertexStep(IN,[located_in],vertex)                                    14          14           0.166     1.77
 NoOpBarrierStep(2500)                                                 14          14           0.346     3.68
 TraversalFilterStep([VertexStep(OUT,[serves],ve...                     4           4           2.817    29.93
   VertexStep(OUT,[serves],vertex)                                     14          14           0.506
   HasStep([cuisine_name.within([fast food, Mexi...                                             0.536
 GroupStep([IdentityStep, ProfileStep],[VertexSt...                     1           1           1.959    20.83
   IdentityStep                                                         4           4           0.045
   VertexStep(IN,[about_a],vertex)                                     12          12           0.283
   PropertiesStep([rating],value)                                      12          12           0.427
   MeanGlobalStep                                                       4           4           0.598
 OrderLocalStep([[values, desc]])                                       1           1           0.481     5.12
 UnfoldStep                                                             2           2           0.051     0.55
 RangeGlobalStep(0,1)                                                   1           1           0.099     1.06
 ProjectStep([restaurant_id, restaurant_name, ad...                     1           1           1.112    11.82
   TraversalMapStep(keys)                                               1           1           0.026
   PropertiesStep([restaurant_id],value)                                1           1           0.090
   TraversalMapStep(keys)                                               1           1           0.018
   PropertiesStep([restaurant_name],value)                              1           1           0.062
   TraversalMapStep(keys)                                               1           1           0.021
   PropertiesStep([address],value)                                      1           1           0.044
   TraversalMapStep(values)                                             1           1           0.016
   TraversalMapStep(keys)                                               1           1           0.013
   VertexStep(OUT,[serves],vertex)                                      1           1           0.051
   PropertiesStep([cuisine_name],value)                                 1           1           0.040
 ReferenceElementStep                                                   1           1           1.180    12.54
 >TOTAL                     -           -           9.411        -
 **/

// TEST 4 of v1 (warm) 12.425 ms
/**
 ==>Traversal Metrics
 Step                                                               Count  Traversers       Time (ms)    % Dur
 =============================================================================================================
 TinkerGraphStep(vertex,[~label.eq(user), user_i...                     1           1           3.703    29.81
 VertexStep(OUT,[lives_in],vertex)                                      1           1           0.327     2.63
 VertexStep(IN,[located_in],vertex)                                    14          14           0.230     1.86
 NoOpBarrierStep(2500)                                                 14          14           0.796     6.41
 TraversalFilterStep([VertexStep(OUT,[serves],ve...                     4           4           2.077    16.72
   VertexStep(OUT,[serves],vertex)                                     14          14           0.749
   HasStep([cuisine_name.within([fast food, Mexi...                                             0.407
 GroupStep([IdentityStep, ProfileStep],[VertexSt...                     1           1           2.989    24.06
   IdentityStep                                                         4           4           0.200
   VertexStep(IN,[about_a],vertex)                                     12          12           0.395
   PropertiesStep([rating],value)                                      12          12           0.523
   MeanGlobalStep                                                       4           4           0.944
 OrderLocalStep([[values, desc]])                                       1           1           0.628     5.05
 UnfoldStep                                                             2           2           0.118     0.96
 RangeGlobalStep(0,1)                                                   1           1           0.129     1.04
 ProjectStep([restaurant_id, restaurant_name, ad...                     1           1           0.703     5.66
   TraversalMapStep(keys)                                               1           1           0.019
   PropertiesStep([restaurant_id],value)                                1           1           0.067
   TraversalMapStep(keys)                                               1           1           0.014
   PropertiesStep([restaurant_name],value)                              1           1           0.029
   TraversalMapStep(keys)                                               1           1           0.014
   PropertiesStep([address],value)                                      1           1           0.026
   TraversalMapStep(values)                                             1           1           0.012
   TraversalMapStep(keys)                                               1           1           0.013
   VertexStep(OUT,[serves],vertex)                                      1           1           0.041
   PropertiesStep([cuisine_name],value)                                 1           1           0.032
 ReferenceElementStep                                                   1           1           0.721     5.80
 >TOTAL                     -           -          12.425        -
 **/

// Looks like we've settled down from a performance point of view. So our top 3 steps are:

// TEST 2
/**
 NoOpBarrierStep(2500)                                                 14          14           2.964    28.38
 GroupStep([IdentityStep, ProfileStep],[VertexSt...                     1           1           2.222    21.28
 TraversalFilterStep([VertexStep(OUT,[serves],ve...                     4           4           1.637    15.68
 TinkerGraphStep(vertex,[~label.eq(user), user_i...                     1           1           1.114    10.67
**/

// TEST 3
/**
 TraversalFilterStep([VertexStep(OUT,[serves],ve...                     4           4           2.817    29.93
 GroupStep([IdentityStep, ProfileStep],[VertexSt...                     1           1           1.959    20.83
 ProjectStep([restaurant_id, restaurant_name, ad...                     1           1           1.112    11.82
 TinkerGraphStep(vertex,[~label.eq(user), user_i...                     1           1           1.084    11.52
**/

// TEST 4
/**
 TinkerGraphStep(vertex,[~label.eq(user), user_i...                     1           1           3.703    29.81
 GroupStep([IdentityStep, ProfileStep],[VertexSt...                     1           1           2.989    24.06
 TraversalFilterStep([VertexStep(OUT,[serves],ve...                     4           4           2.077    16.72
 NoOpBarrierStep(2500)                                                 14          14           0.796     6.41
**/

// Let's start with that TinkerGraphs set. That shouldn't even be showing up in the list and it is all 3 runs.
// what we haven't done is add any indexes. TinkerGraph allows for some nominal indexing functionality
graph.createIndex('user_id',Vertex.class)

// v2: add index on user_id
// TEST 1
/**
 ==>Traversal Metrics
 Step                                                               Count  Traversers       Time (ms)    % Dur
 =============================================================================================================
 TinkerGraphStep(vertex,[~label.eq(user), user_i...                     1           1           4.294    43.70
 VertexStep(OUT,[lives_in],vertex)                                      1           1           0.232     2.37
 VertexStep(IN,[located_in],vertex)                                    14          14           0.144     1.47
 NoOpBarrierStep(2500)                                                 14          14           0.238     2.43
 TraversalFilterStep([VertexStep(OUT,[serves],ve...                     4           4           1.127    11.47
 VertexStep(OUT,[serves],vertex)                                     14          14           0.403
 HasStep([cuisine_name.within([fast food, Mexi...                                             0.335
 GroupStep([IdentityStep, ProfileStep],[VertexSt...                     1           1           1.430    14.55
 IdentityStep                                                         4           4           0.021
 VertexStep(IN,[about_a],vertex)                                     12          12           0.214
 PropertiesStep([rating],value)                                      12          12           0.227
 MeanGlobalStep                                                       4           4           0.549
 OrderLocalStep([[values, desc]])                                       1           1           0.503     5.12
 UnfoldStep                                                             2           2           0.053     0.55
 RangeGlobalStep(0,1)                                                   1           1           0.094     0.96
 ProjectStep([restaurant_id, restaurant_name, ad...                     1           1           1.141    11.62
 TraversalMapStep(keys)                                               1           1           0.013
 PropertiesStep([restaurant_id],value)                                1           1           0.027
 TraversalMapStep(keys)                                               1           1           0.021
 PropertiesStep([restaurant_name],value)                              1           1           0.043
 TraversalMapStep(keys)                                               1           1           0.011
 PropertiesStep([address],value)                                      1           1           0.021
 TraversalMapStep(values)                                             1           1           0.011
 TraversalMapStep(keys)                                               1           1           0.015
 VertexStep(OUT,[serves],vertex)                                      1           1           0.032
 PropertiesStep([cuisine_name],value)                                 1           1           0.035
 ReferenceElementStep                                                   1           1           0.565     5.76
 >TOTAL                     -           -           9.826        -
 gremlin>
 gremlin> g.V().has('user','user_id',uid).
 ......1>   out('lives_in').
 ......2>   in('located_in').
 ......3>   where(out('serves').has('cuisine_name',within(cuisine_list))).
 ......4>   group().
 ......5>     by(identity()).
 ......6>     by(__.in('about_a').values('rating').mean()).
 ......7>   order(local).
 ......8>     by(values, desc).
 ......9>     unfold().
 .....10>   limit(1).
 .....11>   project('restaurant_id','restaurant_name','address','rating_average','cuisine').
 .....12>     by(select(keys).values('restaurant_id')).
 .....13>     by(select(keys).values('restaurant_name')).
 .....14>     by(select(keys).values('address')).
 .....15>     by(select(values)).
 .....16>     by(select(keys).out('serves').values('cuisine_name')).
 .....17>   profile()
 ==>Traversal Metrics
 Step                                                               Count  Traversers       Time (ms)    % Dur
 =============================================================================================================
 TinkerGraphStep(vertex,[~label.eq(user), user_i...                     1           1           0.103     3.43
 VertexStep(OUT,[lives_in],vertex)                                      1           1           0.058     1.96
 VertexStep(IN,[located_in],vertex)                                    14          14           0.065     2.17
 NoOpBarrierStep(2500)                                                 14          14           0.130     4.34
 TraversalFilterStep([VertexStep(OUT,[serves],ve...                     4           4           0.589    19.62
 VertexStep(OUT,[serves],vertex)                                     14          14           0.182
 HasStep([cuisine_name.within([fast food, Mexi...                                             0.137
 GroupStep([IdentityStep, ProfileStep],[VertexSt...                     1           1           0.934    31.10
 IdentityStep                                                         4           4           0.025
 VertexStep(IN,[about_a],vertex)                                     12          12           0.134
 PropertiesStep([rating],value)                                      12          12           0.146
 MeanGlobalStep                                                       4           4           0.359
 OrderLocalStep([[values, desc]])                                       1           1           0.290     9.68
 UnfoldStep                                                             2           2           0.022     0.76
 RangeGlobalStep(0,1)                                                   1           1           0.065     2.17
 ProjectStep([restaurant_id, restaurant_name, ad...                     1           1           0.356    11.86
 TraversalMapStep(keys)                                               1           1           0.012
 PropertiesStep([restaurant_id],value)                                1           1           0.019
 TraversalMapStep(keys)                                               1           1           0.009
 PropertiesStep([restaurant_name],value)                              1           1           0.020
 TraversalMapStep(keys)                                               1           1           0.009
 PropertiesStep([address],value)                                      1           1           0.019
 TraversalMapStep(values)                                             1           1           0.008
 TraversalMapStep(keys)                                               1           1           0.009
 VertexStep(OUT,[serves],vertex)                                      1           1           0.019
 PropertiesStep([cuisine_name],value)                                 1           1           0.019
 ReferenceElementStep                                                   1           1           0.387    12.90
 >TOTAL                     -           -           3.004        -
 gremlin>
 gremlin> g.V().has('user','user_id',uid).
 ......1>   out('lives_in').
 ......2>   in('located_in').
 ......3>   where(out('serves').has('cuisine_name',within(cuisine_list))).
 ......4>   group().
 ......5>     by(identity()).
 ......6>     by(__.in('about_a').values('rating').mean()).
 ......7>   order(local).
 ......8>     by(values, desc).
 ......9>     unfold().
 .....10>   limit(1).
 .....11>   project('restaurant_id','restaurant_name','address','rating_average','cuisine').
 .....12>     by(select(keys).values('restaurant_id')).
 .....13>     by(select(keys).values('restaurant_name')).
 .....14>     by(select(keys).values('address')).
 .....15>     by(select(values)).
 .....16>     by(select(keys).out('serves').values('cuisine_name')).
 .....17>   profile()
 ==>Traversal Metrics
 Step                                                               Count  Traversers       Time (ms)    % Dur
 =============================================================================================================
 TinkerGraphStep(vertex,[~label.eq(user), user_i...                     1           1           0.101     3.01
 VertexStep(OUT,[lives_in],vertex)                                      1           1           0.049     1.47
 VertexStep(IN,[located_in],vertex)                                    14          14           0.064     1.92
 NoOpBarrierStep(2500)                                                 14          14           0.168     4.99
 TraversalFilterStep([VertexStep(OUT,[serves],ve...                     4           4           0.637    18.93
 VertexStep(OUT,[serves],vertex)                                     14          14           0.243
 HasStep([cuisine_name.within([fast food, Mexi...                                             0.134
 GroupStep([IdentityStep, ProfileStep],[VertexSt...                     1           1           1.133    33.65
 IdentityStep                                                         4           4           0.043
 VertexStep(IN,[about_a],vertex)                                     12          12           0.150
 PropertiesStep([rating],value)                                      12          12           0.168
 MeanGlobalStep                                                       4           4           0.472
 OrderLocalStep([[values, desc]])                                       1           1           0.291     8.67
 UnfoldStep                                                             2           2           0.045     1.36
 RangeGlobalStep(0,1)                                                   1           1           0.077     2.31
 ProjectStep([restaurant_id, restaurant_name, ad...                     1           1           0.573    17.02
 TraversalMapStep(keys)                                               1           1           0.016
 PropertiesStep([restaurant_id],value)                                1           1           0.025
 TraversalMapStep(keys)                                               1           1           0.013
 PropertiesStep([restaurant_name],value)                              1           1           0.023
 TraversalMapStep(keys)                                               1           1           0.016
 PropertiesStep([address],value)                                      1           1           0.019
 TraversalMapStep(values)                                             1           1           0.009
 TraversalMapStep(keys)                                               1           1           0.011
 VertexStep(OUT,[serves],vertex)                                      1           1           0.120
 PropertiesStep([cuisine_name],value)                                 1           1           0.024
 ReferenceElementStep                                                   1           1           0.224     6.67
 >TOTAL                     -           -           3.367        -
 gremlin>
 gremlin> g.V().has('user','user_id',uid).
 ......1>   out('lives_in').
 ......2>   in('located_in').
 ......3>   where(out('serves').has('cuisine_name',within(cuisine_list))).
 ......4>   group().
 ......5>     by(identity()).
 ......6>     by(__.in('about_a').values('rating').mean()).
 ......7>   order(local).
 ......8>     by(values, desc).
 ......9>     unfold().
 .....10>   limit(1).
 .....11>   project('restaurant_id','restaurant_name','address','rating_average','cuisine').
 .....12>     by(select(keys).values('restaurant_id')).
 .....13>     by(select(keys).values('restaurant_name')).
 .....14>     by(select(keys).values('address')).
 .....15>     by(select(values)).
 .....16>     by(select(keys).out('serves').values('cuisine_name')).
 .....17>   profile()
 ==>Traversal Metrics
 Step                                                               Count  Traversers       Time (ms)    % Dur
 =============================================================================================================
 TinkerGraphStep(vertex,[~label.eq(user), user_i...                     1           1           0.112     2.99
 VertexStep(OUT,[lives_in],vertex)                                      1           1           0.056     1.51
 VertexStep(IN,[located_in],vertex)                                    14          14           0.067     1.81
 NoOpBarrierStep(2500)                                                 14          14           0.123     3.31
 TraversalFilterStep([VertexStep(OUT,[serves],ve...                     4           4           0.623    16.65
 VertexStep(OUT,[serves],vertex)                                     14          14           0.229
 HasStep([cuisine_name.within([fast food, Mexi...                                             0.147
 GroupStep([IdentityStep, ProfileStep],[VertexSt...                     1           1           1.552    41.46
 IdentityStep                                                         4           4           0.020
 VertexStep(IN,[about_a],vertex)                                     12          12           0.643
 PropertiesStep([rating],value)                                      12          12           0.179
 MeanGlobalStep                                                       4           4           0.408
 OrderLocalStep([[values, desc]])                                       1           1           0.288     7.71
 UnfoldStep                                                             2           2           0.023     0.64
 RangeGlobalStep(0,1)                                                   1           1           0.076     2.05
 ProjectStep([restaurant_id, restaurant_name, ad...                     1           1           0.460    12.29
 TraversalMapStep(keys)                                               1           1           0.015
 PropertiesStep([restaurant_id],value)                                1           1           0.030
 TraversalMapStep(keys)                                               1           1           0.012
 PropertiesStep([restaurant_name],value)                              1           1           0.032
 TraversalMapStep(keys)                                               1           1           0.016
 PropertiesStep([address],value)                                      1           1           0.016
 TraversalMapStep(values)                                             1           1           0.010
 TraversalMapStep(keys)                                               1           1           0.011
 VertexStep(OUT,[serves],vertex)                                      1           1           0.025
 PropertiesStep([cuisine_name],value)                                 1           1           0.023
 ReferenceElementStep                                                   1           1           0.358     9.58
 >TOTAL                     -           -           3.743        -
 gremlin>
 gremlin>

 **/

// Redo ranking -- looking better

// v3 - refactor cuisine vertex to include cuisine on restaurant vertex

g.V().hasLabel('cuisine').valueMap()

g.V().hasLabel('cuisine').as('c').
  in('serves').
    property('cuisine_name', select('c').values('cuisine_name')).
  valueMap()

/**
 ==>Traversal Metrics
 Step                                                               Count  Traversers       Time (ms)    % Dur
 =============================================================================================================
 TinkerGraphStep(vertex,[~label.eq(user), user_i...                     1           1           0.194     2.35
 VertexStep(OUT,[lives_in],vertex)                                      1           1           2.039    24.58
 VertexStep(IN,[located_in],vertex)                                    14          14           0.067     0.82
 NoOpBarrierStep(2500)                                                 14          14           0.111     1.34
 HasStep([cuisine_name.within([fast food, Mexica...                     4           4           0.136     1.65
 GroupStep([IdentityStep, ProfileStep],[VertexSt...                     1           1           1.151    13.88
   IdentityStep                                                         4           4           0.018
   VertexStep(IN,[about_a],vertex)                                     12          12           0.378
   PropertiesStep([rating],value)                                      12          12           0.158
   MeanGlobalStep                                                       4           4           0.299
 OrderLocalStep([[values, desc]])                                       1           1           0.309     3.73
 UnfoldStep                                                             2           2           0.338     4.08
 RangeGlobalStep(0,1)                                                   1           1           0.810     9.77
 ProjectStep([restaurant_id, restaurant_name, ad...                     1           1           2.826    34.07
   TraversalMapStep(keys)                                               1           1           0.020
   PropertiesStep([restaurant_id],value)                                1           1           0.031
   TraversalMapStep(keys)                                               1           1           0.023
   PropertiesStep([restaurant_name],value)                              1           1           0.024
   TraversalMapStep(keys)                                               1           1           0.013
   PropertiesStep([address],value)                                      1           1           0.027
   TraversalMapStep(values)                                             1           1           1.102
   TraversalMapStep(keys)                                               1           1           0.028
   PropertiesStep([cuisine_name],value)                                 1           1           0.598
 ReferenceElementStep                                                   1           1           0.309     3.73
 >TOTAL                     -           -           8.294        -
 gremlin> g.V().has('user','user_id',uid).
 ......1>   out('lives_in').
 ......2>   in('located_in').
 ......3>   has('cuisine_name',within(cuisine_list)).
 ......4>   group().
 ......5>     by(identity()).
 ......6>     by(__.in('about_a').values('rating').mean()).
 ......7>   order(local).
 ......8>     by(values, desc).
 ......9>     unfold().
 .....10>   limit(1).
 .....11>   project('restaurant_id','restaurant_name','address','rating_average','cuisine').
 .....12>     by(select(keys).values('restaurant_id')).
 .....13>     by(select(keys).values('restaurant_name')).
 .....14>     by(select(keys).values('address')).
 .....15>     by(select(values)).
 .....16>     by(select(keys).values('cuisine_name')).
 .....17>   profile()
 ==>Traversal Metrics
 Step                                                               Count  Traversers       Time (ms)    % Dur
 =============================================================================================================
 TinkerGraphStep(vertex,[~label.eq(user), user_i...                     1           1           0.097     3.89
 VertexStep(OUT,[lives_in],vertex)                                      1           1           0.039     1.57
 VertexStep(IN,[located_in],vertex)                                    14          14           0.040     1.60
 NoOpBarrierStep(2500)                                                 14          14           0.076     3.04
 HasStep([cuisine_name.within([fast food, Mexica...                     4           4           0.055     2.21
 GroupStep([IdentityStep, ProfileStep],[VertexSt...                     1           1           0.557    22.24
   IdentityStep                                                         4           4           0.011
   VertexStep(IN,[about_a],vertex)                                     12          12           0.079
   PropertiesStep([rating],value)                                      12          12           0.107
   MeanGlobalStep                                                       4           4           0.164
 OrderLocalStep([[values, desc]])                                       1           1           0.301    12.03
 UnfoldStep                                                             2           2           0.024     0.97
 RangeGlobalStep(0,1)                                                   1           1           0.069     2.78
 ProjectStep([restaurant_id, restaurant_name, ad...                     1           1           1.079    43.04
   TraversalMapStep(keys)                                               1           1           0.009
   PropertiesStep([restaurant_id],value)                                1           1           0.016
   TraversalMapStep(keys)                                               1           1           0.006
   PropertiesStep([restaurant_name],value)                              1           1           0.025
   TraversalMapStep(keys)                                               1           1           0.006
   PropertiesStep([address],value)                                      1           1           0.023
   TraversalMapStep(values)                                             1           1           0.006
   TraversalMapStep(keys)                                               1           1           0.005
   PropertiesStep([cuisine_name],value)                                 1           1           0.011
 ReferenceElementStep                                                   1           1           0.166     6.63
 >TOTAL                     -           -           2.509        -
 gremlin> g.V().has('user','user_id',uid).
 ......1>   out('lives_in').
 ......2>   in('located_in').
 ......3>   has('cuisine_name',within(cuisine_list)).
 ......4>   group().
 ......5>     by(identity()).
 ......6>     by(__.in('about_a').values('rating').mean()).
 ......7>   order(local).
 ......8>     by(values, desc).
 ......9>     unfold().
 .....10>   limit(1).
 .....11>   project('restaurant_id','restaurant_name','address','rating_average','cuisine').
 .....12>     by(select(keys).values('restaurant_id')).
 .....13>     by(select(keys).values('restaurant_name')).
 .....14>     by(select(keys).values('address')).
 .....15>     by(select(values)).
 .....16>     by(select(keys).values('cuisine_name')).
 .....17>   profile()
 ==>Traversal Metrics
 Step                                                               Count  Traversers       Time (ms)    % Dur
 =============================================================================================================
 TinkerGraphStep(vertex,[~label.eq(user), user_i...                     1           1           0.072     4.63
 VertexStep(OUT,[lives_in],vertex)                                      1           1           0.061     3.93
 VertexStep(IN,[located_in],vertex)                                    14          14           0.059     3.79
 NoOpBarrierStep(2500)                                                 14          14           0.225    14.27
 HasStep([cuisine_name.within([fast food, Mexica...                     4           4           0.041     2.62
 GroupStep([IdentityStep, ProfileStep],[VertexSt...                     1           1           0.395    25.04
   IdentityStep                                                         4           4           0.008
   VertexStep(IN,[about_a],vertex)                                     12          12           0.060
   PropertiesStep([rating],value)                                      12          12           0.079
   MeanGlobalStep                                                       4           4           0.112
 OrderLocalStep([[values, desc]])                                       1           1           0.168    10.66
 UnfoldStep                                                             2           2           0.025     1.63
 RangeGlobalStep(0,1)                                                   1           1           0.052     3.31
 ProjectStep([restaurant_id, restaurant_name, ad...                     1           1           0.274    17.37
   TraversalMapStep(keys)                                               1           1           0.008
   PropertiesStep([restaurant_id],value)                                1           1           0.012
   TraversalMapStep(keys)                                               1           1           0.007
   PropertiesStep([restaurant_name],value)                              1           1           0.013
   TraversalMapStep(keys)                                               1           1           0.006
   PropertiesStep([address],value)                                      1           1           0.012
   TraversalMapStep(values)                                             1           1           0.006
   TraversalMapStep(keys)                                               1           1           0.019
   PropertiesStep([cuisine_name],value)                                 1           1           0.012
 ReferenceElementStep                                                   1           1           0.201    12.75
 >TOTAL                     -           -           1.577        -
 gremlin> g.V().has('user','user_id',uid).
 ......1>   out('lives_in').
 ......2>   in('located_in').
 ......3>   has('cuisine_name',within(cuisine_list)).
 ......4>   group().
 ......5>     by(identity()).
 ......6>     by(__.in('about_a').values('rating').mean()).
 ......7>   order(local).
 ......8>     by(values, desc).
 ......9>     unfold().
 .....10>   limit(1).
 .....11>   project('restaurant_id','restaurant_name','address','rating_average','cuisine').
 .....12>     by(select(keys).values('restaurant_id')).
 .....13>     by(select(keys).values('restaurant_name')).
 .....14>     by(select(keys).values('address')).
 .....15>     by(select(values)).
 .....16>     by(select(keys).values('cuisine_name')).
 .....17>   profile()
 ==>Traversal Metrics
 Step                                                               Count  Traversers       Time (ms)    % Dur
 =============================================================================================================
 TinkerGraphStep(vertex,[~label.eq(user), user_i...                     1           1           0.153     8.52
 VertexStep(OUT,[lives_in],vertex)                                      1           1           0.241    13.38
 VertexStep(IN,[located_in],vertex)                                    14          14           0.058     3.24
 NoOpBarrierStep(2500)                                                 14          14           0.055     3.08
 HasStep([cuisine_name.within([fast food, Mexica...                     4           4           0.055     3.07
 GroupStep([IdentityStep, ProfileStep],[VertexSt...                     1           1           0.455    25.23
   IdentityStep                                                         4           4           0.009
   VertexStep(IN,[about_a],vertex)                                     12          12           0.061
   PropertiesStep([rating],value)                                      12          12           0.059
   MeanGlobalStep                                                       4           4           0.157
 OrderLocalStep([[values, desc]])                                       1           1           0.134     7.47
 UnfoldStep                                                             2           2           0.020     1.14
 RangeGlobalStep(0,1)                                                   1           1           0.056     3.15
 ProjectStep([restaurant_id, restaurant_name, ad...                     1           1           0.345    19.15
   TraversalMapStep(keys)                                               1           1           0.009
   PropertiesStep([restaurant_id],value)                                1           1           0.028
   TraversalMapStep(keys)                                               1           1           0.009
   PropertiesStep([restaurant_name],value)                              1           1           0.024
   TraversalMapStep(keys)                                               1           1           0.020
   PropertiesStep([address],value)                                      1           1           0.012
   TraversalMapStep(values)                                             1           1           0.006
   TraversalMapStep(keys)                                               1           1           0.006
   PropertiesStep([cuisine_name],value)                                 1           1           0.013
 ReferenceElementStep                                                   1           1           0.226    12.58
 >TOTAL                     -           -           1.804        -
 **/
