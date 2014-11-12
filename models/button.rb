class Button < ActiveRecord::Base
  belongs_to :kiosk, :class_name => 'Kiosk', :foreign_key => 'kiosk_id'
  belongs_to :trap, :class_name => 'Trap', :foreign_key => 'trap_id'
  validates_presence_of :kiosk_id
  validates_presence_of :trap_id
  I18n.enforce_available_locales = true
end