package software.sava.anchor.programs.pyth.receiver.anchor.types;

import java.util.function.BiFunction;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;
import software.sava.core.programs.Discriminator;
import software.sava.core.rpc.Filter;
import software.sava.rpc.json.http.response.AccountInfo;

import static software.sava.anchor.AnchorUtil.parseDiscriminator;
import static software.sava.core.accounts.PublicKey.readPubKey;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record Config(PublicKey _address,
                     Discriminator discriminator,
                     PublicKey governanceAuthority,
                     PublicKey targetGovernanceAuthority,
                     PublicKey wormhole,
                     DataSource[] validDataSources,
                     long singleUpdateFeeInLamports,
                     int minimumSignatures) implements Borsh {

  public static final int GOVERNANCE_AUTHORITY_OFFSET = 8;
  public static final int TARGET_GOVERNANCE_AUTHORITY_OFFSET = 40;

  public static Filter createGovernanceAuthorityFilter(final PublicKey governanceAuthority) {
    return Filter.createMemCompFilter(GOVERNANCE_AUTHORITY_OFFSET, governanceAuthority);
  }

  public static Filter createTargetGovernanceAuthorityFilter(final PublicKey targetGovernanceAuthority) {
    final byte[] _data = new byte[33];
    _data[0] = 1;
    targetGovernanceAuthority.write(_data, 1);
    return Filter.createMemCompFilter(TARGET_GOVERNANCE_AUTHORITY_OFFSET, _data);
  }

  public static Config read(final byte[] _data, final int offset) {
    return read(null, _data, offset);
  }

  public static Config read(final AccountInfo<byte[]> accountInfo) {
    return read(accountInfo.pubKey(), accountInfo.data(), 0);
  }

  public static Config read(final PublicKey _address, final byte[] _data) {
    return read(_address, _data, 0);
  }

  public static final BiFunction<PublicKey, byte[], Config> FACTORY = Config::read;

  public static Config read(final PublicKey _address, final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    final var discriminator = parseDiscriminator(_data, offset);
    int i = offset + discriminator.length();
    final var governanceAuthority = readPubKey(_data, i);
    i += 32;
    final var targetGovernanceAuthority = _data[i++] == 0 ? null : readPubKey(_data, i);
    if (targetGovernanceAuthority != null) {
      i += 32;
    }
    final var wormhole = readPubKey(_data, i);
    i += 32;
    final var validDataSources = Borsh.readVector(DataSource.class, DataSource::read, _data, i);
    i += Borsh.lenVector(validDataSources);
    final var singleUpdateFeeInLamports = getInt64LE(_data, i);
    i += 8;
    final var minimumSignatures = _data[i] & 0xFF;
    return new Config(_address,
                      discriminator,
                      governanceAuthority,
                      targetGovernanceAuthority,
                      wormhole,
                      validDataSources,
                      singleUpdateFeeInLamports,
                      minimumSignatures);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset + discriminator.write(_data, offset);
    governanceAuthority.write(_data, i);
    i += 32;
    i += Borsh.writeOptional(targetGovernanceAuthority, _data, i);
    wormhole.write(_data, i);
    i += 32;
    i += Borsh.writeVector(validDataSources, _data, i);
    putInt64LE(_data, i, singleUpdateFeeInLamports);
    i += 8;
    _data[i] = (byte) minimumSignatures;
    ++i;
    return i - offset;
  }

  @Override
  public int l() {
    return 8 + 32
         + (targetGovernanceAuthority == null ? 1 : (1 + 32))
         + 32
         + Borsh.lenVector(validDataSources)
         + 8
         + 1;
  }
}
