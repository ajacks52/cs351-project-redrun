class Trap < ActiveRecord::Base
  belongs_to :kiosk
  has_one :button
  I18n.enforce_available_locales = true
end