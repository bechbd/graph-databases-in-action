g.addE('works_with').from(dave).to(josh).
    property('start_year',2016).property('end_year',2017).
  addE('works_with').from(dave).to(ted).
    property('start_year',2016).property('end_year',2017).
  addE('works_with').from(josh).to(ted).
    property('start_year',2016).property('end_year',2019).
  addE('works_with').from(dave).to(hank).
    property('start_year',2017).property('end_year',2018).
  addE('works_with').from(dave).to(kelly).
    property('start_year',2018).
  addE('works_with').from(dave).to(denise).
    property('start_year',2018).
  addE('works_with').from(denise).to(kelly).
    property('start_year',2018).iterate()




g.V().fold().project('v','e').by(unfold().count()).by(unfold().outE().count())


g.V().has('person','name','Josh').
  until(has('person','name','Denise')).
  repeat(bothE('works_with').otherV().simplePath()).
  path()

g.V().has('person','name','Josh').
  repeat(bothE('works_with').otherV()).times(2).path()

g.V().has('person','name','Josh').
        repeat(bothE('works_with').otherV().simplePath()).times(2).path()


g.V().has('person','name','Ted').
  until(has('person', 'name', 'Denise')).
  emit().
  repeat(
    bothE('works_with').otherV().simplePath()
  ).path()





g.V().has('person','name','Ted').as('self').
    both().both().
    project('foff','f').
      by('name').
      by(
          both().where(
                        both().as('self')
                      ).
          values('name')
        )
