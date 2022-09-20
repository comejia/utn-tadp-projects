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
        entry = @@mocks[self.to_s]
        unless entry.nil?
          entry.each do |key, value|
            obj.singleton_class.send(:define_method, key, &value)
          end
        end
        obj
      end
    end
  end