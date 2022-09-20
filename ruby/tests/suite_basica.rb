class SuiteBasica

  def testear_que_un_numero_es_igual_a_si_mismo
    7.deberia ser 7
  end

  def testear_que_verdadero_siempre_es_verdadero
    true.deberia ser true
  end

  def testear_que_un_numero_es_menor_a_otro
    20.deberia ser menor_a 100
  end

  def testear_que_un_numero_es_mayor_a_otro
    100.deberia ser mayor_a 20
  end

  def si_no_es_un_test_no_se_ejecuta
    puts "Ejecutame!!"
  end
end
