class Test
  attr_accessor :name, :status, :reason

  def initialize(name)
    @name = name
  end

  def execute(context)
    @status = context.send(@name)
  end

end
