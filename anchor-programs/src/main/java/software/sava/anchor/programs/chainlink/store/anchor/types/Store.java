package software.sava.anchor.programs.chainlink.store.anchor.types;

import java.util.function.BiFunction;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;
import software.sava.core.programs.Discriminator;
import software.sava.core.rpc.Filter;
import software.sava.rpc.json.http.response.AccountInfo;

import static software.sava.anchor.AnchorUtil.parseDiscriminator;
import static software.sava.core.accounts.PublicKey.readPubKey;

public record Store(PublicKey _address,
                    Discriminator discriminator,
                    PublicKey owner,
                    PublicKey proposedOwner,
                    PublicKey loweringAccessController) implements Borsh {

  public static final int BYTES = 104;
  public static final Filter SIZE_FILTER = Filter.createDataSizeFilter(BYTES);

  public static final int OWNER_OFFSET = 8;
  public static final int PROPOSED_OWNER_OFFSET = 40;
  public static final int LOWERING_ACCESS_CONTROLLER_OFFSET = 72;

  public static Filter createOwnerFilter(final PublicKey owner) {
    return Filter.createMemCompFilter(OWNER_OFFSET, owner);
  }

  public static Filter createProposedOwnerFilter(final PublicKey proposedOwner) {
    return Filter.createMemCompFilter(PROPOSED_OWNER_OFFSET, proposedOwner);
  }

  public static Filter createLoweringAccessControllerFilter(final PublicKey loweringAccessController) {
    return Filter.createMemCompFilter(LOWERING_ACCESS_CONTROLLER_OFFSET, loweringAccessController);
  }

  public static Store read(final byte[] _data, final int offset) {
    return read(null, _data, offset);
  }

  public static Store read(final AccountInfo<byte[]> accountInfo) {
    return read(accountInfo.pubKey(), accountInfo.data(), 0);
  }

  public static Store read(final PublicKey _address, final byte[] _data) {
    return read(_address, _data, 0);
  }

  public static final BiFunction<PublicKey, byte[], Store> FACTORY = Store::read;

  public static Store read(final PublicKey _address, final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    final var discriminator = parseDiscriminator(_data, offset);
    int i = offset + discriminator.length();
    final var owner = readPubKey(_data, i);
    i += 32;
    final var proposedOwner = readPubKey(_data, i);
    i += 32;
    final var loweringAccessController = readPubKey(_data, i);
    return new Store(_address, discriminator, owner, proposedOwner, loweringAccessController);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset + discriminator.write(_data, offset);
    owner.write(_data, i);
    i += 32;
    proposedOwner.write(_data, i);
    i += 32;
    loweringAccessController.write(_data, i);
    i += 32;
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
