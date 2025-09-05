package software.sava.anchor.programs.glam.protocol.anchor.types;

import java.lang.Boolean;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;

public record EmergencyAccessUpdateArgs(PublicKey[] disabledIntegrations,
                                        PublicKey[] disabledDelegates,
                                        Boolean stateEnabled) implements Borsh {

  public static EmergencyAccessUpdateArgs read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var disabledIntegrations = Borsh.readPublicKeyVector(_data, i);
    i += Borsh.lenVector(disabledIntegrations);
    final var disabledDelegates = Borsh.readPublicKeyVector(_data, i);
    i += Borsh.lenVector(disabledDelegates);
    final var stateEnabled = _data[i++] == 0 ? null : _data[i] == 1;
    return new EmergencyAccessUpdateArgs(disabledIntegrations, disabledDelegates, stateEnabled);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    i += Borsh.writeVector(disabledIntegrations, _data, i);
    i += Borsh.writeVector(disabledDelegates, _data, i);
    i += Borsh.writeOptional(stateEnabled, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return Borsh.lenVector(disabledIntegrations) + Borsh.lenVector(disabledDelegates) + (stateEnabled == null ? 1 : (1 + 1));
  }
}
