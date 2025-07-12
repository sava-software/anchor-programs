package software.sava.anchor.programs.switchboard.on_demand.anchor.types;

import software.sava.core.borsh.Borsh;

public record OracleHeartbeatV2Params(byte[] gatewayUri) implements Borsh {

  public static OracleHeartbeatV2Params read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    final var gatewayUri = _data[offset] == 0 ? null : new byte[64];
    if (gatewayUri != null) {
      Borsh.readArray(gatewayUri, _data, offset + 1);
    }
    return new OracleHeartbeatV2Params(gatewayUri);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    if (gatewayUri == null || gatewayUri.length == 0) {
      _data[i++] = 0;
    } else {
      _data[i++] = 1;
      i += Borsh.writeArray(gatewayUri, _data, i);
    }
    return i - offset;
  }

  @Override
  public int l() {
    return (gatewayUri == null || gatewayUri.length == 0 ? 1 : (1 + Borsh.lenArray(gatewayUri)));
  }
}
