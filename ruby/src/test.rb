class Test
  attr_reader :status

  def initialize(name)
    @test_name = name
    @status = false
    @reason = ""
  end

  def execute(context)
    @status = context.send(@test_name)
  end

end
