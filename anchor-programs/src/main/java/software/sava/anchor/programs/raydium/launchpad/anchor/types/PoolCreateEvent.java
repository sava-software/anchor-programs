package software.sava.anchor.programs.raydium.launchpad.anchor.types;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;

import static software.sava.core.accounts.PublicKey.readPubKey;

// Emitted when pool created
public record PoolCreateEvent(PublicKey poolState,
                              PublicKey creator,
                              PublicKey config,
                              MintParams baseMintParam,
                              CurveParams curveParam,
                              VestingParams vestingParam) implements Borsh {

  public static PoolCreateEvent read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var poolState = readPubKey(_data, i);
    i += 32;
    final var creator = readPubKey(_data, i);
    i += 32;
    final var config = readPubKey(_data, i);
    i += 32;
    final var baseMintParam = MintParams.read(_data, i);
    i += Borsh.len(baseMintParam);
    final var curveParam = CurveParams.read(_data, i);
    i += Borsh.len(curveParam);
    final var vestingParam = VestingParams.read(_data, i);
    return new PoolCreateEvent(poolState,
                               creator,
                               config,
                               baseMintParam,
                               curveParam,
                               vestingParam);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    poolState.write(_data, i);
    i += 32;
    creator.write(_data, i);
    i += 32;
    config.write(_data, i);
    i += 32;
    i += Borsh.write(baseMintParam, _data, i);
    i += Borsh.write(curveParam, _data, i);
    i += Borsh.write(vestingParam, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return 32
         + 32
         + 32
         + Borsh.len(baseMintParam)
         + Borsh.len(curveParam)
         + Borsh.len(vestingParam);
  }
}
