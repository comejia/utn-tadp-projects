class Test
  attr_reader :status, :test_name

  def initialize(name)
    @test_name = name
    @status = {}
  end

  def execute(context)
    @status =
      begin
        context.send(@test_name)
      rescue => e
        {
          result: "broken",
          description: e.message + "\n" + e.backtrace.join("\n")
        }
      end
    puts(@test_name, @status.fetch(:description))
  end

  def failed?
    @status.fetch(:result) == false
  end

  def passed?
    @status.fetch(:result) == true
  end

  def broken?
    @status.fetch(:result) == "broken"
  end

end
