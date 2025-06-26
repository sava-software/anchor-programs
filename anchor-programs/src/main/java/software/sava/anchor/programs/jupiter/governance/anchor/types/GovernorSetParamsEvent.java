package software.sava.anchor.programs.jupiter.governance.anchor.types;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;

import static software.sava.core.accounts.PublicKey.readPubKey;

public record GovernorSetParamsEvent(PublicKey governor,
                                     GovernanceParameters prevParams,
                                     GovernanceParameters params) implements Borsh {

  public static final int BYTES = 96;

  public static GovernorSetParamsEvent read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var governor = readPubKey(_data, i);
    i += 32;
    final var prevParams = GovernanceParameters.read(_data, i);
    i += Borsh.len(prevParams);
    final var params = GovernanceParameters.read(_data, i);
    return new GovernorSetParamsEvent(governor, prevParams, params);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    governor.write(_data, i);
    i += 32;
    i += Borsh.write(prevParams, _data, i);
    i += Borsh.write(params, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
