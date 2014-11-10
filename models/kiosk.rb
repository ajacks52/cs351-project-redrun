class Kiosk < ActiveRecord::Base
  has_one :trap
  has_one :button
  I18n.enforce_available_locales = true
end