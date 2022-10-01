module Mocks
  @@mocks = {}

  refine Module do
    def mockear(method_name, &block)
      self.class_eval do
        entry = @@mocks[self.to_s]
        if entry.nil?
          entry = {}
        end
        entry[method_name] = block
        @@mocks[self.to_s] = entry
      end
    end
  end

  refine Class do
    def new(*args, &block)
      obj = super *args, &block
      ancestors = self.ancestors.map(&:to_s)
      entries = ancestors.map { |ancestor| @@mocks[ancestor] }.compact
      entries.each do |entry|
        entry.each do |key, value|
          obj.singleton_class.send(:define_method, key, &value)
        end
      end
      obj
    end
  end
end