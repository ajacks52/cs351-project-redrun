class Trap < ActiveRecord::Base
  belongs_to :kiosk, :class_name => 'Kiosk', :foreign_key => 'kiosk_id'
  validates_presence_of :kiosk_id
  has_one :button, :class_name => 'Button'
  I18n.enforce_available_locales = true
end