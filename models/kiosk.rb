class Kiosk < ActiveRecord::Base
  validates_presence_of :location
  validates_presence_of :cooldown
  has_one :trap, :class_name => "Trap"
  has_one :button, :class_name => "Button"
  I18n.enforce_available_locales = true
end