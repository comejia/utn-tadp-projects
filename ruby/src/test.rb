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

  def description
    @status.fetch(:description)
  end

end
