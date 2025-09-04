package software.sava.anchor.programs.loopscale.anchor.types;

import java.util.function.BiFunction;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;
import software.sava.core.programs.Discriminator;
import software.sava.core.rpc.Filter;
import software.sava.rpc.json.http.response.AccountInfo;

import static software.sava.core.accounts.PublicKey.readPubKey;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;
import static software.sava.core.programs.Discriminator.createAnchorDiscriminator;
import static software.sava.core.programs.Discriminator.toDiscriminator;

public record Timelock(PublicKey _address,
                       Discriminator discriminator,
                       PublicKey vault,
                       long initTimestamp,
                       long executionDelay,
                       TimelockUpdateParams params) implements Borsh {

  public static final Discriminator DISCRIMINATOR = toDiscriminator(189, 33, 78, 75, 205, 31, 4, 177);
  public static final Filter DISCRIMINATOR_FILTER = Filter.createMemCompFilter(0, DISCRIMINATOR.data());

  public static final int VAULT_OFFSET = 8;
  public static final int INIT_TIMESTAMP_OFFSET = 40;
  public static final int EXECUTION_DELAY_OFFSET = 48;
  public static final int PARAMS_OFFSET = 56;

  public static Filter createVaultFilter(final PublicKey vault) {
    return Filter.createMemCompFilter(VAULT_OFFSET, vault);
  }

  public static Filter createInitTimestampFilter(final long initTimestamp) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, initTimestamp);
    return Filter.createMemCompFilter(INIT_TIMESTAMP_OFFSET, _data);
  }

  public static Filter createExecutionDelayFilter(final long executionDelay) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, executionDelay);
    return Filter.createMemCompFilter(EXECUTION_DELAY_OFFSET, _data);
  }

  public static Filter createParamsFilter(final TimelockUpdateParams params) {
    return Filter.createMemCompFilter(PARAMS_OFFSET, params.write());
  }

  public static Timelock read(final byte[] _data, final int offset) {
    return read(null, _data, offset);
  }

  public static Timelock read(final AccountInfo<byte[]> accountInfo) {
    return read(accountInfo.pubKey(), accountInfo.data(), 0);
  }

  public static Timelock read(final PublicKey _address, final byte[] _data) {
    return read(_address, _data, 0);
  }

  public static final BiFunction<PublicKey, byte[], Timelock> FACTORY = Timelock::read;

  public static Timelock read(final PublicKey _address, final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    final var discriminator = createAnchorDiscriminator(_data, offset);
    int i = offset + discriminator.length();
    final var vault = readPubKey(_data, i);
    i += 32;
    final var initTimestamp = getInt64LE(_data, i);
    i += 8;
    final var executionDelay = getInt64LE(_data, i);
    i += 8;
    final var params = TimelockUpdateParams.read(_data, i);
    return new Timelock(_address,
                        discriminator,
                        vault,
                        initTimestamp,
                        executionDelay,
                        params);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset + discriminator.write(_data, offset);
    vault.write(_data, i);
    i += 32;
    putInt64LE(_data, i, initTimestamp);
    i += 8;
    putInt64LE(_data, i, executionDelay);
    i += 8;
    i += Borsh.write(params, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return 8 + 32 + 8 + 8 + Borsh.len(params);
  }
}
