package heroes

class Grupo(val heroes: List[Aventurero]) {

  var cofreComun: Set[Item] = Set()

  def aventureros(): List[Aventurero] = heroes.filter(h => !h.muerto())

  def lider(): Aventurero = aventureros().head
}

class Item