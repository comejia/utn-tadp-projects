package heroes

class Grupo(val heroes: List[Aventurero]) {

  var cofreComun: Set[Item] = Set()

  var lider: Aventurero = heroesVivos().head

  def heroesVivos(): List[Aventurero] = heroes.filter(h => !h.muerto())
}

class Item