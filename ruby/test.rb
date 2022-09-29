require_relative './src/tad_spec'
require_relative './tests/suites'
require 'colorize'

# TADSpec.testear SuitePersona
module SuiteModule
    def testear_que_un_numero_es_igual_a_si_mismo1
        7.deberia ser 7
      end
    
      def testear_que_verdadero_siempre_es_verdadero1
        1/0
      end
end

class SuiteTest < SuiteBasica
    include SuiteModule
end

TADSpec.testear SuiteTest