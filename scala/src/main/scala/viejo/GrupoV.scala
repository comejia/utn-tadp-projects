//package viejo
//
//import heroes.Aventurero
//
//class Grupo(val heroes: List[Aventurero]) {
//
//  var cofreComun: Set[Item] = Set()
//
//  def aventureros(): List[Aventurero] = heroes.filter(h => !h.muerto())
//
//  def lider(): Aventurero = aventureros().head
//
//  def tieneLlave(): Boolean = cofreComun.contains(c => c.equals(LLave))
//}
//
//class Item