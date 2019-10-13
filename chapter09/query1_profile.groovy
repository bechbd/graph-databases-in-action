gremlin> sg.V().hasLabel('review').
......1>   out('about').
......2>   where(out('located').has('city','name',city_name)).
......3>   group().
......4>   by(identity()).
......5>   by(__.in('about').values('rating').mean()).
......6>   order(local).
......7>   by(values, desc).
......8>   limit(local,10).
......9>   unfold().
.....10>   project('restaurant_id','restaurant_name','address','rating_average').
.....11>   by(select(keys).values('restaurant_id')).
.....12>   by(select(keys).values('restaurant_name')).
.....13>   by(select(keys).values('address')).
.....14>   by(select(values)).profile()
  ==>Traversal Metrics
Step                                                               Count  Traversers       Time (ms)    % Dur
=============================================================================================================
TinkerGraphStep(vertex,[~label.eq(review)])                           81          81           5.150    28.35
VertexStep(OUT,[about],vertex)                                        81          81           0.591     3.25
TraversalFilterStep([VertexStep(OUT,[located],v...                    59          59           1.549     8.53
VertexStep(OUT,[located],vertex)                                    76          76           0.998
HasStep([~label.eq(city), name.eq(Houston)])                                                 0.124
GroupStep([IdentityStep, ProfileStep],[VertexSt...                     1           1           4.702    25.88
IdentityStep                                                        59          59           0.049
VertexStep(IN,[about],vertex)                                      173         173           0.441
PropertiesStep([rating],value)                                     173         173           1.299
MeanGlobalStep                                                      23          23           2.207
OrderLocalStep([[values, desc]])                                       1           1           0.468     2.58
RangeLocalStep(0,10)                                                   1           1           0.095     0.52
UnfoldStep                                                            10          10           0.403     2.22
NoOpBarrierStep(2500)                                                 10          10           1.468     8.08
ProjectStep([restaurant_id, restaurant_name, ad...                    10          10           3.739    20.58
TraversalMapStep(keys)                                              10          10           0.083
PropertiesStep([restaurant_id],value)                               10          10           0.033
TraversalMapStep(keys)                                              10          10           0.011
PropertiesStep([restaurant_name],value)                             10          10           0.024
TraversalMapStep(keys)                                              10          10           0.006
PropertiesStep([address],value)                                     10          10           0.014
TraversalMapStep(values)                                            10          10           0.011
  >TOTAL                     -           -          18.169        -
  gremlin> sg.V().hasLabel('review').
......1>   out('about').
......2>   where(out('located').has('city','name',city_name)).
......3>   group().
......4>   by(identity()).
......5>   by(__.in('about').values('rating').mean()).
......6>   order(local).
......7>   by(values, desc).
......8>   limit(local,10).
......9>   unfold().
.....10>   project('restaurant_id','restaurant_name','address','rating_average').
.....11>   by(select(keys).values('restaurant_id')).
.....12>   by(select(keys).values('restaurant_name')).
.....13>   by(select(keys).values('address')).
.....14>   by(select(values)).profile()
==>Traversal Metrics
Step                                                               Count  Traversers       Time (ms)    % Dur
=============================================================================================================
TinkerGraphStep(vertex,[~label.eq(review)])                           81          81           0.409     2.54
VertexStep(OUT,[about],vertex)                                        81          81           0.341     2.11
TraversalFilterStep([VertexStep(OUT,[located],v...                    59          59           6.213    38.46
  VertexStep(OUT,[located],vertex)                                    76          76           1.235
  HasStep([~label.eq(city), name.eq(Houston)])                                                 0.195
GroupStep([IdentityStep, ProfileStep],[VertexSt...                     1           1           6.646    41.14
  IdentityStep                                                        59          59           0.048
  VertexStep(IN,[about],vertex)                                      173         173           0.775
  PropertiesStep([rating],value)                                     173         173           1.557
  MeanGlobalStep                                                      23          23           3.065
OrderLocalStep([[values, desc]])                                       1           1           1.690    10.46
RangeLocalStep(0,10)                                                   1           1           0.046     0.29
UnfoldStep                                                            10          10           0.022     0.14
NoOpBarrierStep(2500)                                                 10          10           0.029     0.18
ProjectStep([restaurant_id, restaurant_name, ad...                    10          10           0.755     4.67
  TraversalMapStep(keys)                                              10          10           0.076
  PropertiesStep([restaurant_id],value)                               10          10           0.098
  TraversalMapStep(keys)                                              10          10           0.015
  PropertiesStep([restaurant_name],value)                             10          10           0.105
  TraversalMapStep(keys)                                              10          10           0.052
  PropertiesStep([address],value)                                     10          10           0.040
  TraversalMapStep(values)                                            10          10           0.012
                                            >TOTAL                     -           -          16.155        -
gremlin> sg.V().hasLabel('review').
......1>   out('about').
......2>   where(out('located').has('city','name',city_name)).
......3>   group().
......4>   by(identity()).
......5>   by(__.in('about').values('rating').mean()).
......6>   order(local).
......7>   by(values, desc).
......8>   limit(local,10).
......9>   unfold().
.....10>   project('restaurant_id','restaurant_name','address','rating_average').
.....11>   by(select(keys).values('restaurant_id')).
.....12>   by(select(keys).values('restaurant_name')).
.....13>   by(select(keys).values('address')).
.....14>   by(select(values)).profile()
==>Traversal Metrics
Step                                                               Count  Traversers       Time (ms)    % Dur
=============================================================================================================
TinkerGraphStep(vertex,[~label.eq(review)])                           81          81           1.174     5.23
VertexStep(OUT,[about],vertex)                                        81          81           2.280    10.16
TraversalFilterStep([VertexStep(OUT,[located],v...                    59          59           4.315    19.22
VertexStep(OUT,[located],vertex)                                    76          76           2.315
HasStep([~label.eq(city), name.eq(Houston)])                                                 0.503
GroupStep([IdentityStep, ProfileStep],[VertexSt...                     1           1          10.324    46.00
IdentityStep                                                        59          59           0.159
VertexStep(IN,[about],vertex)                                      173         173           5.334
PropertiesStep([rating],value)                                     173         173           0.832
MeanGlobalStep                                                      23          23           1.289
OrderLocalStep([[values, desc]])                                       1           1           1.635     7.29
RangeLocalStep(0,10)                                                   1           1           0.050     0.22
UnfoldStep                                                            10          10           0.047     0.21
NoOpBarrierStep(2500)                                                 10          10           0.067     0.30
ProjectStep([restaurant_id, restaurant_name, ad...                    10          10           2.551    11.37
TraversalMapStep(keys)                                              10          10           0.019
PropertiesStep([restaurant_id],value)                               10          10           0.046
TraversalMapStep(keys)                                              10          10           0.016
PropertiesStep([restaurant_name],value)                             10          10           0.042
TraversalMapStep(keys)                                              10          10           0.029
PropertiesStep([address],value)                                     10          10           1.546
TraversalMapStep(values)                                            10          10           0.013
  >TOTAL                     -           -          22.446        -
