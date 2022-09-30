require_relative 'persona'

class SuiteExceptions
  def testear_que_no_se_puede_dividir_por_0
    en { 7 / 0 }.deberia explotar_con ZeroDivisionError
  end

  def testear_que_no_existe_un_metodo
    p = Persona.new(30)
    en { p.nombre }.deberia explotar_con NoMethodError
  end

  def testear_que_no_existe_un_metodo_con_otro_error_jerarquico
    p = Persona.new(30)
    en { p.nombre }.deberia explotar_con NameError
  end

  def testear_que_si_existe_mensaje_no_hay_error_y_falla
    p = Persona.new(30)
    en { p.viejo? }.deberia explotar_con NoMethodError
  end

  def testear_que_evaluar_un_error_con_otro_falla
    en { 7 / 0 }.deberia explotar_con NoMethodError
  end
end
