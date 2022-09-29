class Test
  attr_reader :status, :test_name, :reason

  def initialize(name)
    @test_name = name
    @status = false
    @reason = ""
  end

  def execute(context)
    @status = begin 
      context.send(@test_name)
    rescue => e
      @reason = e.message
      "broken"
    end
  end

end
